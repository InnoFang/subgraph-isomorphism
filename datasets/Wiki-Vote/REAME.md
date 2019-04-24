<h1>Wikipedia vote network</h1>
<h3>Dataset information</h3>
<p><a href="http://www.wikipedia.org">Wikipedia</a> is a free encyclopedia written collaboratively by volunteers around the world. A small part of Wikipedia contributors are administrators, who are users with access to additional technical features that aid in maintenance. In order for a user to become an administrator a Request for adminship (RfA) is issued and the Wikipedia community via a public discussion or a vote decides who to promote to adminship. Using the latest complete dump of Wikipedia page edit history (from January 3 2008) we extracted all administrator elections and vote history data. This gave us 2,794 elections with 103,663 total votes and 7,066 users participating in the elections (either casting a vote or being voted on). Out of these 1,235 elections resulted in a successful promotion, while 1,559 elections did not result in the promotion. About half of the votes in the dataset are by existing admins, while the other half comes from ordinary Wikipedia users.</p>
<p>The network contains all the Wikipedia voting data from the inception of Wikipedia till January 2008. Nodes in the network represent wikipedia users and a directed edge from node <i>i</i> to node <i>j</i> represents that user <i>i</i> voted on user <i>j</i>.</p>
<table id="datatab" summary="Dataset statistics">
<tr> <th colspan="2">Dataset statistics</th> </tr>
<tr><td>Nodes</td> <td>7115</td></tr>
<tr><td>Edges</td> <td>103689</td></tr>
<tr><td>Nodes in largest WCC</td> <td>7066 (0.993)</td></tr>
<tr><td>Edges in largest WCC</td> <td>103663 (1.000)</td></tr>
<tr><td>Nodes in largest SCC</td> <td>1300 (0.183)</td></tr>
<tr><td>Edges in largest SCC</td> <td>39456 (0.381)</td></tr>
<tr><td>Average clustering coefficient</td> <td>0.1409</td></tr>
<tr><td>Number of triangles</td> <td>608389</td></tr>
<tr><td>Fraction of closed triangles</td> <td>0.04564</td></tr>
<tr><td>Diameter (longest shortest path)</td> <td>7</td></tr>
<tr><td>90-percentile effective diameter</td> <td>3.8</td></tr>
</table>
<br />
<p>Raw Wikipedia adminship election data that was used to create the Wiki-vote network can be accessed <a href="wiki-Elec.html">here</a>.</p>
<br />
<h3>Source (citation)</h3>
<ul>
<li> J. Leskovec, D. Huttenlocher, J. Kleinberg. <a href="http://cs.stanford.edu/people/jure/pubs/triads-chi10.pdf">Signed Networks in Social Media</a>. CHI 2010.</li>
<li> J. Leskovec, D. Huttenlocher, J. Kleinberg. <a href="http://cs.stanford.edu/people/jure/pubs/signs-www10.pdf">Predicting Positive and Negative Links in Online Social Networks</a>. WWW 2010.</li>
</ul>
<br />
 