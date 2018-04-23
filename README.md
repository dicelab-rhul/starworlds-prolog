# README #

## SWI Prolog (recommended) ##

Requires SWI prolog to be installed. 
To begin, locate the SWI prolog directory on your machine, something like C:\Program Files (x86)\swipl\ and locate the jpl jar, (located in the lib folder), add this jar to your projects build path.
The SWI prolog engine is automatically loaded by the JPL jar.
To construct an agent mind in prolog, you will need to add the following line to the file,

:- module(name, [perceive/2, decide/2]).

where name is the name of your agent mind.

JPL is limited to only a single SWIPL VM and so each agent mind (of a java agent) is created as a module.
Each mind file (module) must be declared to the system so that it can be used later, this is done using
SWIPrologAgentMind.declareMind(_fullpath_). This will return the module name as a String. 
This name should then be given as an argument to the constructor of a SWIPrologAgentMind, see SWIGridAgentMind for an example.

A prolog mind currently requires the following two predicates to be defined:

perceive/2(+Perceptions, +Time). Where Perceptions is a list of perception terms and Time is an integer.
decide/2(-Actions, +Time). Where Actions is a list of (or term) actions and time is an integer.

//TODO talk about term mappers and more details on the modularisation of minds within the VM.

//TODO talk about how it is possible to change the perceive and decide predicates to what ever is wanted (but we must create a java mind to do so, like TuPrologAgentMind or SWIPrologAgentMind.


## Tu Prolog ##

Download the latest jars,

* starworlds-lite.jar
* tuprolog.jar
* 2p.jar

from the downloads section in this repo, add them to the build path of your project.

Take a look at the GridWorld example files, especially the mapper classes, agent mind classes and test class.

a Tuprolog mind also requires the perceive and decide predicates as above to be defined.






