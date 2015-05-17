# Tedapt #

### Status
[![Build Status](https://travis-ci.org/drexedam/Tedapt.svg?branch=master)](https://travis-ci.org/drexedam/Tedapt)

## Structure ##

### at.fhv.tedapt ###
Full Eclipse project containing the developed plug-in

### at.fhv.tedapt.library.flyaway ###
Flyway core library as plugin to be used by at.fhv.tedapt
**NOTE:** there is a typo in the project name!

### at.fhv.tedapt.library.jsonsimple ###
JSON.simple library as plugin to be used by at.fhv.tedapt

### at.fhv.tedapt.library.jooq ###
jOOQ library as plugin to be used by at.fhv.tedapt

### at.fhv.tedapt.feature ###
Eclipse feature

### at.fhv.tedapt.releng.p2 ###
Plugin update site

### at.fhv.tedapt.test ###
Project for testing

### ExampleIntegration ###
#### metamodel_and_editor ####
* Library
    * Contains the metamodel and its history plus the generated model code
* Library.edit
    * Generated edit code
* Library.editor
    * Generated and modified editor
* Library.tests
    * Generated test project
####model/LibraryExample####
Project containing a library model and therefore uses the modified editor

### at.fhv.tedapt.customexample ###
A project containing an Edapt and Flyway custom migration