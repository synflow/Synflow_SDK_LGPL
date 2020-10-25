# The Synflow SDK LGPL

The Synflow SDK is the compiler + IDE for the Cx programming language. See http://cx-lang.org for more details and documentation about the language.

# License

The code is under GNU General Public License v3.0 LGPL. Some files are also licensed under the BSD license (as indicated in the header of said files).

Copyright © `2015-2020` `Synflow, a trademark of NextDF EIRL`

# Install

### Prerequisites

* You need Java 8 to run the IDE so prior to execute the Synflow IDE, it is a good idea to install a flavor of Java 8 JDK.
* You also need Eclipse to run the IDE so download and install Eclipse for RCP and RAP developers.

### Set Up

A step by step explanation that tells you how to get your development environment up and running.

1. Clone the repository under Eclipse (Clone Repository)

2. Set Up Eclipse (Preferences => Workspace => Text File Encoding => UTF-8) and display the Git Repositories View (Eclipse => Show View => Git Repositories)

3. Install the plugins (Eclipse Modeling framework SDK, Graphical Editing Framework GEF SDK, MWE 2 Language SDK, MWE 2 Runtime SDK, Xtend IDE, Xtext Complete SDK)

4. Execute MWE 2 on the GenerateCx.mw2 class (Project Explorer => Synflow IDE => com.synflow.cx => src => com.synflow.cx => GenerateCx.mw2 ) by making a right click and selecting Run as => MWE 2 Workflow

5.  Configure the Product (Project Explorer => Synflow IDE => releng => ngDesign.product => Synchronize, and then Lunch an Eclipse Application - it will fail)

6.  Run the product (Run as => ngdesign.product => Plug-ins => Add Required Plug-ins, and then Apply and Run)

### Run and test it
See our [documentation](https://synflow.gitlab.io/docs/) or [tutorial](https://synflow.gitlab.io/courses/) to learn how to use our tool.

## Built With

* [Eclipse](https://www.eclipse.org/) - The integrated development environment used in computer programming, and the most widely used Java IDE.

## Contributing

Please read [CONTRIBUTING.md]() for details on our code of conduct, and the process for submitting pull requests to us.

## Versioning

For the versions available, see the [tags on this repository](https://gitlab.com/synflow/ngdesign_gplv3/tags).

## Authors

* **Nicolas Siret** - [Nicolas06](https://gitlab.com/nicolas06)
* **Matthieu Wipliez** - [matt2xu](https://gitlab.com/matt2xu)
