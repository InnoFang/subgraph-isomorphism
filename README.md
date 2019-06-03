# Subgraph Isomorphism

❄Implement the common subgraph isomorphism algorithms (i.e. Ullmann, VF2) based on `MapReduce` on `Hadoop`. 

More infomartion, please see [wiki](https://github.com/InnoFang/subgraph-isomorphism/wiki)

More usage, please see test cases:
 + [GraphReaderTest](https://github.com/InnoFang/subgraph-isomorphism/blob/master/src/test/java/io/github/innofang/graph/GraphReaderTest.java)
 + [UllmannTest](https://github.com/InnoFang/subgraph-isomorphism/blob/master/src/test/java/io/github/innofang/lib/UllmannTest.java)
 + [VF2Test](https://github.com/InnoFang/subgraph-isomorphism/blob/master/src/test/java/io/github/innofang/lib/VF2Test.java)

[Download](https://github.com/InnoFang/subgraph-isomorphism/releases) jar files for a try!

## What's subgraph isomorphism problem?

In theoretical computer science, the subgraph isomorphism problem is a computational task in which two graphs G and H are given as input, and one must determine whether G contains a subgraph that is isomorphic to H.

## the UML of Implementation of SubGraph Isomorphism Algorithms

 the encapsulation and optimization of the Ullmann algorithm and the VF2 algorithm are carried out. The UML as follow:

![](https://raw.githubusercontent.com/InnoFang/jotter/image-hosting/subgraph-isomorphism/the%20UML%20of%20Implementation%20of%20Subgraph%20Isomorphism%20Algorithms.png)

## Flow Diagram of the MapReduce Process

![](https://raw.githubusercontent.com/InnoFang/jotter/image-hosting/subgraph-isomorphism/Flow%20Diagram%20of%20the%20MapReduce%20Process.jpg)


## Reference & Bibliography

 + Wikipedia contributors. (2018, October 13). Subgraph isomorphism problem. In Wikipedia, The Free Encyclopedia. Retrieved 09:12, April 8, 2019, from https://en.wikipedia.org/w/index.php?title=Subgraph_isomorphism_problem&oldid=863918223

 + Ullmann J R. An algorithm for subgraph isomorphism[J]. Journal of the ACM (JACM), 1976, 23(1): 31-42. [link](https://www.cs.bgu.ac.il/~dinitz/Course/SS-12/Ullman_Algorithm.pdf)

 + Cordella L P, Foggia P and Sansone C. A (sub)graph isomorphism algorithm for matching large graphs[J]. IEEE PAMI, 2004, 26(10):1367–1372. [link](https://ieeexplore.ieee.org/document/1323804?arnumber=1323804&tag=1)

 + Ashish Sharma, Santosh Bahir, Sushant Narsale, Unmil Tambe, "A  Parallel Algorithm for Finding Sub-graph Isomorphism", CS420-ProjectReport  (www.cs.jhu.edu/~snarsal/CS420-ProjectReport.pdf), CS420: Parallel Programming. Fall 2008.

## [License](./LICENSE)

    Subgraph Isomorphism: Implement the common subgraph isomorphism algorithms (i.e. Ullmann, VF2) based on MapReduce on Hadoop 
    Copyright (C) 2019  InnoFang

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
