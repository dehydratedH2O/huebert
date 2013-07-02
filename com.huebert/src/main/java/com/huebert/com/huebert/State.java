package com.huebert.com.huebert;

/** (c) 2013 mkb
 *  This file is part of Huebert.
 *
 *  Huebert is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Huebert is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with Huebert.  If not, see <http://www.gnu.org/licenses/>.
**/

class State {
	boolean on;
	int bri, hue, sat;
	float[] xy;
	int ct;
	String alert, effect, colormode;
	boolean reachable;
}