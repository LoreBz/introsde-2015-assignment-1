**Introduction to Service Design and Engineering | University of Trento**

## Description
This repository contains code written to satisfy tasks reported in Assignment01.

## Author
Lorenzo Ghiro | lorenzo.ghiro@studenti.unitn.it

## How to run
Simply type this in a terminal **ant execute.evaluation** and all necessary compilation/generation will be done, afterwards each of the required tasks will be executed as well.

In the present case ant source file build.xml is annotated and the main targets are:

* **compile** : will call init, download required libraries and tools, generate java models from XSD definitions via JAXB XJC, compile source code and build the project
* **execute.XPathTestAdvance** : take care of the instructions required to fulfill tasks related to lab03
* **execute.JAXBMarshaller** : provides an example of JAXB to perform marshalling
* **execute.JAXBUnMarshaller** : provides an example of JAXB to perform unmarshalling
* **execute.PeopleJson** : provides an example of marshalling to JSON format
 
some sub targets are used to check if output files are alredy available, avoiding the execution of the same java class when no more required.

## Documentation and organization
Given the simplicity of the project the code is not structured in a particular way or in many different packages. However basic comments are placed next to the most meaningful lines of code.

## Dataset
a random generated xml db of person is stored in *delivery/people.xml*. That file is generated with a convenient java class that is executed before running the **execute.XPathTestAdvance** target, so that a db of people is available when testing the execution of some xpath queries

## Libraries
The project depends on a set of specific libraries in order to fullfill its requirements.
All dependencies are managed with ivy and can be found inside ivy.xml

### Main libraries

* **JAXB**: xml related
* **Jackson**: json related. Additional modules to read-write Date objects **Jackson-DataType-Joda**

## Build 
Running the project requires java and ant.

* **Java** version 1.8.0+
* **Ant** version 1.9+


### Link

https://sites.google.com/a/unitn.it/introsde_2015-16/lab-sessions/assignments/assignment-1
