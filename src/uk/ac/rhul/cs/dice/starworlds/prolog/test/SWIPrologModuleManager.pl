%generate module ids
:- dynamic mcount/1.
mcount(0).
mid(Z):-
	mcount(X),
	retract(mcount(X)),
	Y is X + 1,
	asserta(mcount(Y)),
	atom_concat(module, Y, Z).

declare_mind(SourceFile, SourceModule):-
	use_module(SourceFile),
	module_property(SourceModule, file(SourceFile)).

% creates a new module, each agent mind should use a new module when running
new_mind(ImportList, SourceModule, Module):-
	mid(Module),
	importPredicates(ImportList, SourceModule, Module).

importPredicates([P|T], SourceModule, Module):-
	SourceModule:export(P),
	Module:import(P),
	importPredicates(T, SourceModule, Module).

importPredicates([], _, _).

