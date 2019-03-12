# Subgraph Isomorphism

Design and implement the common subgraph isomorphic algorithm based on `MapReduce` programming model on `Hadoop` platform

## What's subgraph isomorphism problem?

see the [wiki](https://en.wikipedia.org/wiki/Subgraph_isomorphism_problem)

---

<div align="center">

<strong>Classification of Representative Graph Pattern Matching Algorithm</strong> [1]

</div>

| Algorithm | Structural Matching | Semantic Matching |  Exact Matching | Inexact Matching | Optimal Solution | Approximate Solution | Static Graph Matching | Dynamic Graph Matching |
| - | - | - | - | - | - | - | - | - |
| Ullmann <br/> (1976)      | Yes | No  | Yes | No  | Yes | No  | Yes | No  |
| VF2 <br/> (2001)          | Yes | Yes | Yes | No  | Yes | No  | Yes | No  |
| QuickSI <br/> (2008)      | No  | Yes | Yes | No  | Yes | No  | Yes | No  |
| GraphQL <br/> (2008)      | No  | Yes | Yes | No  | Yes | No  | Yes | No  |
| GADDI <br/> (2009)        | Yes | Yes | Yes | No  | Yes | No  | Yes | No  |
| SPath <br/> (2010)        | No  | Yes | Yes | No  | Yes | No  | Yes | No  |
| GraphGrep <br/> (2002)    | No  | Yes | Yes | No  | Yes | No  | Yes | Yes |
| CTree <br/> (2006)        | No  | Yes | Yes | Yes | Yes | No  | Yes | No  |
| GCoding <br/> (2008)      | No  | Yes | Yes | No  | Yes | No  | Yes | No  |
| GIndex <br/> (2004)       | Yes | Yes | Yes | No  | Yes | No  | Yes | No  |
| FG-Index <br/> (2007)     | No  | Yes | Yes | No  | Yes | No  | Yes | No  |
| Tree+Delta <br/> (2007)   | No  | Yes | Yes | No  | Yes | No  | Yes | No  |
| SUBDUE <br/> (1994)       | Yes | No  | No  | Yes | Yes | No  | Yes | No  |
| LAW <br/> (2003)          | No  | Yes | No  | Yes | Yes | No  | Yes | No  |
| NPV <br/> (2010)          | No  | Yes | Yes | No  | Yes | No  | Yes | Yes |
| IncMatch <br/> (2012)     | No  | Yes | Yes | No  | Yes | No  | Yes | Yes |
| StreamWorks <br/> (2013)  | No  | Yes | Yes | No  | Yes | No  | No  | Yes |
| Umcyama <br/> (1998)      | No  | Yes | Yes | No  | No  | Yes | Yes | No  |
| Christmas <br/> (1995)    | Yes | No  | Yes | No  | No  | Yes | Yes | No  |

## TO-DO for SubGraph Isomorphism Algorithm Implementation

 + [x] [Ullmann](./src/main/java/io/github/innofang/algorithm/impl/Ullmann.java) [2]
 + [x] [VF2](./src/main/java/io/github/innofang/algorithm/impl/VF2.java) [3]
 + ...
 
## Reference & Bibliography

[1] 于静，刘燕兵，张宇，刘梦雅，谭建龙，郭莉.大规模图数据匹配技术综述[J].计算机研究与发展，2015，52(2): 391-409

[2] Ullmann J R. An algorithm for subgraph isomorphism[J]. Journal of the ACM (JACM), 1976, 23(1): 31-42.

[3] Cordella L P, Foggia P and Sansone C. A (sub)graph isomorphism algorithm for matching large graphs[J]. IEEE PAMI, 2004, 26(10):1367–1372. 

## [License](./LICENSE)

    Subgraph Isomorphism: Design and implement the latest subgraph isomorphic algorithm based on MapReduce programming model on Hadoop platform
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
