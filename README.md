# README #

# Getting started #

Download the latest jars from the downloads section in this repo, add them to the build path of your project.

Take a look at the GridWorld example files, most specifically the GridExampleMind to see how to implement a mind in prolog.

A prolog mind currently requires the following two predicates to be defined:

perceive/2(+Perceptions, +Time). Where Perceptions is a list of perception terms and Time is an integer.
decide/2(-Actions, +Time). Where Actions is a list of (or term) actions and time is an integer.

- Currently working on integrating SWI prolog.


