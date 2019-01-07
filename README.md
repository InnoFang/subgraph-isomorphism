# Subgraph Isomorphism

Design and implement the latest subgraph isomorphic algorithm based on MapReduce programming model on Hadoop platform

## What's subgraph isomorphism problem?

see the [wiki](https://en.wikipedia.org/wiki/Subgraph_isomorphism_problem)


<br />
<div align="center">
<strong>Classification of Representative Graph Pattern Matching Algorithm</strong> [1]
</div>


Algorithm | Structural Matching | Semantic Matching |  Exact Matching | Inexact Matching | Optimal Solution | Approximate Solution | Static Graph Matching | Dynamic Graph Matching 
- | - | - | - | - | - | - | - |-
Ullmann (1976) | Yes | No  | Yes | No  | Yes | No  | Yes | No 
VF2 (2001)     | Yes | Yes | Yes | No  | Yes | No  | Yes | No 
QuickSI (2008) | No  | Yes | Yes | No  | Yes | No  | Yes | No
GraphQL (2008) | No  | Yes | Yes | No  | Yes | No  | Yes | No
GADDI (2009)   | Yes | Yes | Yes | No  | Yes | No  | Yes | No
SPath ( 2010)   | No  | Yes | Yes | No  | Yes | No  | Yes | No
GraphGrep (2002)| No  | Yes | Yes | No  | Yes | No  | Yes | Yes
CTree (2006)   | No  | Yes | Yes | Yes | Yes | No  | Yes | No
GCoding (2008) | No  | Yes | Yes | No  | Yes | No  | Yes | No
GIndex (2004)  | Yes | Yes | Yes  |No  |Yes  |No  |Yes  |No
FG-Index (2007)|  NO|  Yes|  Yes|  No|  Yes|  No|  Yes|  No
Tree+Delta (2007)  |No  |Yes  |Yes  |No | Yes | No|  Yes|  No
SUBDUE (1994) | Yes|  No|  No|  Yes|  Yes|  No|  Yes|  No
LAW (2003) | No | Yes | No  |Yes | Yes |  No  |Yes | No
NPV (2010) | No | Yes | Yes | No | Yes | No|  Yes | Yes|
IncMatch (2012)|  No | Yes | Yes | No | Yes | No | Yes|  Yes
StreamWorks (2013)|  No | Yes | Yes | NO|  Yes | No | No 
YesUmcyama (1998)  |No | Yes  |Yes | No | No |  Yes|  Yes  
NoChristmas (1995)  |Yes | No | No | No | Yes | Yes | No


## Reference

[1] 于静，刘燕兵，张宇，刘梦雅，谭建龙，郭莉.大规模图数据匹配技术综述[J].计算机研究与发展，2015，52(2): 391-409
