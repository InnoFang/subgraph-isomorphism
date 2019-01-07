# Subgraph Isomorphism

Design and implement the latest subgraph isomorphic algorithm based on MapReduce programming model on Hadoop platform

## What's subgraph isomorphism problem?

see the [wiki](https://en.wikipedia.org/wiki/Subgraph_isomorphism_problem)

<br />

<div align="center">

<strong>Classification of Representative Graph Pattern Matching Algorithm</strong> [1]


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

</div>

## Reference

[1] 于静，刘燕兵，张宇，刘梦雅，谭建龙，郭莉.大规模图数据匹配技术综述[J].计算机研究与发展，2015，52(2): 391-409
