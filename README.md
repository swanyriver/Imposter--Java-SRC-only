Imposter--Java-SRC-only
=======================

A test platform for a game concept involving eyes looking at a target

This game combines a previously written Class of mine Eyeballs with some new elements and is designed as a test of a
few gameplay elements i wanted to test out. They are:
*Motion in the periphail vision catching attention
*users eyes naturally following the focus of onscreen characters eyes
*use of "working" eyeballs to add life to onscreen characters

The goal of the game is to watch they eyes of the onscreen character to see wich one is actually an "Imposter"
The "Fly" serves as a distraction.

In the course of developing this game I got sort of of course in the development of a Windy Path class for generating a 
random and curvy path for the fly to follow.  I had certain charicteristics i wanted from this path,  I devised a plan of 
generating random points and then drawing curves from midpoint to midpoint of lines generated from these points using the 
point as a the control point for a bezier curve.
I then created restictions on the generated lines using vector generation (lenght plus radian) and then resticting radians
available to prevent from double backwards (created too sharp of an angle) or going in a direction where the boundry was 
closer than the desired minimum lenght.


A couple of the methods in the Eyeball class were taken from contributers on Stack OverFlow, I have taken an effort to 
deliniate this in the Code.  Other than that this is all original code, writen over the course of 6 months or so, off an on.
