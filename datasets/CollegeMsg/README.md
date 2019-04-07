<h1><a href="https://snap.stanford.edu/data/CollegeMsg.html">CollegeMsg temporal network</a></h1>
<h3>Dataset information</h3>
<p>
This dataset is comprised of private messages sent on an online social network
at the University of California, Irvine. Users could search the network for
others and then initiate conversation based on profile information. An edge (u,
v, t) means that user u sent a private message to user v at time t.  The dataset
here is derived from the
one <a href="https://toreopsahl.com/datasets/#online_social_network">hosted by
Tore Opsahl</a>, but we have parsed it so that it can be loaded directly into
SNAP as a temporal network.
</p>

<table id="datatab" summary="Dataset statistics">
  <tr> <th colspan="2">Dataset statistics</th> </tr>
  <tr><td>Nodes</td> <td>1899</td></tr>
  <tr><td>Temporal Edges</td> <td>59835</td></tr>
  <tr><td>Edges in static graph</td> <td>20296</td></tr>
  <tr><td>Time span</td> <td>193 days</td></tr>
</table>

<h3>Source (citation)</h3>
<ul>
  <li>Pietro Panzarasa, Tore Opsahl, and Kathleen M. Carley. "Patterns and
  dynamics of users' behavior and interaction: Network analysis of an online
  community." Journal of the American Society for Information Science and
  Technology 60.5 (2009): 911-932.</li>
</ul> 

<h3>Data format</h3>
<blockquote>SRC DST UNIXTS</blockquote>
<p>where edges are separated by a new line and
  <ul>
    <li><tt>SRC</tt>: id of the source node (a user)</li>
    <li><tt>TGT</tt>: id of the target node (a user)</li>
    <li><tt>UNIXTS</tt>: Unix timestamp (seconds since the epoch)</li>
  </ul>
</p>
