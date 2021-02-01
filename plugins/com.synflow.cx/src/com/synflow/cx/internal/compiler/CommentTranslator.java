/*
 * This file is part of the ngDesign SDK.
 *
 * Copyright (c) 2019 - 2021 Synflow SAS.
 *
 * ngDesign is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * ngDesign is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with ngDesign.  If not, see <https://www.gnu.org/licenses/>.
 *
 */
package com.synflow.cx.internal.compiler;

import static com.synflow.core.IProperties.PROP_COMMENTS;
import static com.synflow.core.IProperties.PROP_COPYRIGHT;
import static com.synflow.core.IProperties.PROP_JAVADOC;
import static com.synflow.models.util.SwitchUtil.CASCADE;
import static com.synflow.models.util.SwitchUtil.DONE;
import static com.synflow.models.util.SwitchUtil.visit;

import java.util.regex.Pattern;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.TerminalRule;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.synflow.cx.CxUtil;
import com.synflow.cx.cx.Block;
import com.synflow.cx.cx.Branch;
import com.synflow.cx.cx.Bundle;
import com.synflow.cx.cx.CxEntity;
import com.synflow.cx.cx.Inst;
import com.synflow.cx.cx.Module;
import com.synflow.cx.cx.Network;
import com.synflow.cx.cx.Statement;
import com.synflow.cx.cx.StatementIf;
import com.synflow.cx.cx.StatementLabeled;
import com.synflow.cx.cx.StatementLoop;
import com.synflow.cx.cx.Task;
import com.synflow.cx.cx.VarDecl;
import com.synflow.cx.cx.Variable;
import com.synflow.cx.cx.util.CxSwitch;
import com.synflow.cx.instantiation.IInstantiator;
import com.synflow.models.dpn.Entity;
import com.synflow.models.util.Executable;
import com.synflow.models.util.Void;

/**
 * This class defines a comment translator that saves comments in properties. It extends CxSwitch
 * rather than VoidCxSwitch because we actually want to have most of the caseStatement* cascade back
 * to the caseStatement method.
 *
 * @author Matthieu Wipliez
 *
 */
public class CommentTranslator extends CxSwitch<Void> {

	private static final Pattern PATTERN_STAR = Pattern.compile("\\*+");

	/**
	 * Adds a line to the given line array. If the line begins with the given sequence, removes that
	 * sequence from the beginning of the string.
	 *
	 * @param lines
	 *            JSON line array
	 * @param line
	 *            a line
	 * @param sequence
	 *            <code>" "</code> or <code>" * "</code>
	 */
	private static void addLine(JsonArray lines, String line, String sequence) {
		if (line.startsWith(sequence)) {
			line = line.substring(sequence.length());
		}
		lines.add(new JsonPrimitive(line));
	}

	/**
	 * This method is called for the first and last line of multi-line comments.
	 *
	 * @param lines
	 *            JSON line array
	 * @param line
	 *            current line
	 */
	private static void addLineSpecial(JsonArray lines, String line) {
		line = PATTERN_STAR.matcher(line).replaceFirst("");
		line = line.trim();
		if (!line.isEmpty()) {
			lines.add(new JsonPrimitive(line));
		}
	}

	private static void visitLeaf(JsonArray lines, ILeafNode leaf, String text) {
		if (leaf.isHidden()) {
			EObject grammarElement = leaf.getGrammarElement();
			if (grammarElement instanceof TerminalRule) {
				TerminalRule rule = (TerminalRule) grammarElement;
				String name = rule.getName();
				if ("SL_COMMENT".equals(name)) {
					lines.add(new JsonPrimitive(text.substring(2).trim()));
				} else if ("ML_COMMENT".equals(name)) {
					String contents = text.substring(1, text.length() - 2);
					String[] lineArray = contents.split("\\r?\\n");
					if (lineArray.length == 1) {
						// only one line
						addLine(lines, lineArray[0], " ");
					} else {
						// multiple lines
						addLineSpecial(lines, lineArray[0]);

						int n = lineArray.length - 1;
						for (int i = 1; i < n; i++) {
							addLine(lines, lineArray[i], " * ");
						}

						addLineSpecial(lines, lineArray[n]);
					}
				}
			}
		}
	}

	private JsonObject comments;

	private JsonArray copyright;

	private IInstantiator instantiator;

	public CommentTranslator(IInstantiator instantiator) {
		this.instantiator = instantiator;
	}

	@Override
	public Void caseBlock(Block block) {
		return visit(this, block.getStmts());
	}

	@Override
	public Void caseBranch(Branch stmt) {
		return visit(this, stmt.getBody());
	}

	@Override
	public Void caseBundle(Bundle bundle) {
		setJavadoc(bundle, CxUtil.getVariables(bundle));
		return DONE;
	}

	@Override
	public Void caseInst(Inst inst) {
		return visit(this, inst.getTask());
	}

	@Override
	public Void caseModule(Module module) {
		translateCopyright(module);
		return visit(this, module.getEntities());
	}

	@Override
	public Void caseNetwork(Network network) {
		setJavadoc(network, null);
		return visit(this, network.getInstances());
	}

	@Override
	public Void caseStatement(Statement stmt) {
		ICompositeNode node = NodeModelUtils.getNode(stmt);
		JsonArray lines = getCommentLines(node);
		if (lines.size() != 0) {
			comments.add(String.valueOf(node.getStartLine()), lines);
		}
		return DONE;
	}

	@Override
	public Void caseStatementIf(StatementIf stmt) {
		visit(this, stmt.getBranches());
		return CASCADE;
	}

	@Override
	public Void caseStatementLabeled(StatementLabeled stmt) {
		visit(this, stmt.getStmt());
		return CASCADE;
	}

	@Override
	public Void caseStatementLoop(StatementLoop stmt) {
		visit(this, stmt.getBody());
		return CASCADE;
	}

	@Override
	public Void caseTask(Task task) {
		setJavadoc(task, CxUtil.getVariables(task));
		return DONE;
	}

	@Override
	public Void caseVarDecl(VarDecl decl) {
		return visit(this, decl.getVariables());
	}

	@Override
	public Void caseVariable(Variable variable) {
		return visit(this, variable.getBody());
	}

	/**
	 * Returns all comments lines up to the first non-hidden leaf node.
	 *
	 * @param node
	 *            node to start from
	 * @return an array of lines (may be empty but not <code>null</code>)
	 */
	private JsonArray getCommentLines(ICompositeNode node) {
		JsonArray lines = new JsonArray();
		for (ILeafNode leaf : node.getLeafNodes()) {
			if (!leaf.isHidden()) {
				break;
			}

			visitLeaf(lines, leaf, leaf.getText());
		}
		return lines;
	}

	/**
	 * Sets copyright and javadoc for the given entity.
	 *
	 * @param cxEntity
	 *            Cx entity
	 * @param decls
	 *            state variables
	 */
	private void setJavadoc(final CxEntity cxEntity, final Iterable<Variable> variables) {
		instantiator.forEachMapping(cxEntity, new Executable<Entity>() {
			@Override
			public void exec(Entity entity) {
				JsonObject properties = entity.getProperties();
				if (copyright != null) {
					properties.add(PROP_COPYRIGHT, copyright);
				}

				ICompositeNode node = NodeModelUtils.getNode(cxEntity);
				JsonArray lines = getCommentLines(node);
				if (lines.size() > 0) {
					properties.add(PROP_JAVADOC, lines);
				}

				if (variables != null) {
					comments = new JsonObject();
					visit(CommentTranslator.this, variables);
					properties.add(PROP_COMMENTS, comments);
				}
			}
		});
	}

	private void translateCopyright(Module module) {
		ICompositeNode root = NodeModelUtils.getNode(module).getRootNode();
		JsonArray lines = getCommentLines(root);
		if (lines.size() > 0) {
			copyright = lines;
		}
	}

}
