:- module(example, [perceive/2, decide/2]). % this is required!

perceive([Perception|Tail], Time):-
	perceive(Perception, Time).
	
perceive(gridpercept(Tiles), Time). %:-
	% do some things!

% do something interesting...
decide([move(east)], Time).