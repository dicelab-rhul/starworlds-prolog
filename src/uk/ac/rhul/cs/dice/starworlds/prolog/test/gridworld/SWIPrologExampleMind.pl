:- module(example, [perceive/2, decide/2]). % this is required!

perceive([Perception|Tail], Time):-
	perceive(Perception, Time).
	
perceive(gridpercept(Tiles), Time). %:-
	% do some things!

% do something interesting...
decide([component(actuator, message(hello, [3]))], Time):-
	0 is mod(Time, 2).
	
decide([component(actuator, move(direction(east)))], Time):-
	1 is mod(Time, 2).