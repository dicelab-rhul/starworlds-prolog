% Perceive - update knowledge base with the tiles that the agent has perceived
% the perception that is received is of the form:
% gridpercept(tile(c(SX,SY), self), [tile(c(X1,Y1), Value), ... tile(c(X9, Y9), Value)])
% currently there is only a single perception in the perception list


perceive([Perception|Tail], Time) :-
	perceive(Perception, Time).

perceive([], Time).

% update knowledge base with perception
perceive(gridpercept(Self, Tiles), Time) :-
	updateTile(Tiles, Time),
	updateSelf(Self, Time).

% we have already updated the tile that was self.
updateSelf(tile(c(X,Y), self), Time) :-
	asserta(s(tile(c(X,Y), self), Time)).

updateTile([tile(c(X,Y), goal)|Tail], Time) :-
	asserta(g(tile(c(X,Y), goal), Time)),
	updateTile(Tail, Time).

updateTile([tile(c(X,Y), empty)|Tail], Time) :-
	retractOldTile(t(tile(c(X,Y), _), _)),
	asserta(t(tile(c(X,Y), empty), Time)),
	updateTile(Tail, Time).

updateTile([tile(c(X,Y), agent(A))|Tail], Time) :-
	retractOldTile(t(tile(c(X,Y), _), _)),
	asserta(t(tile(c(X,Y), agent(A)), Time)),
	updateTile(Tail, Time).

updateTile([], _).

retractOldTile(C):-
	retract(C), !.
retractOldTile(_).

%goal has been reached
decide([],Time):-
	g(tile(c(X,Y), goal), _),
	s(tile(c(X,Y), self), _), !.

%move towards the goal if the agent knows where it is
decide([move(D)], Time):-
	g(tile(c(GX,GY), goal), _),
	s(tile(c(SX,SY), self), _),
	direction(GX, GY, SX, SY, D).

%move to an empty tile
decide(move(east), Time).
	%t(tile(c(SX,SY), self), _).
	
	
%1 is east of 2
direction(X1, Y1, X2, Y2, east) :-
	X1 > X2.
direction(X1, Y1, X2, Y2, west) :-
	X1 < X2.
direction(X1, Y1, X2, Y2, north) :-
	Y1 < Y2.
direction(X1, Y1, X2, Y2, south) :-
	Y1 > Y2.
	
	


	
	
	
	

