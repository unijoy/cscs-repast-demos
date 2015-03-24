# Introduction #

This project is a continuation of a project started with the support of Google Summer of Code 2011, in order to improve [Repast Simphony](http://repast.sourceforge.net/). We have two different broad goals:
  * Project #1: Create Agent Based Model _"Demos"_ using Repast Simphony
  * Project #2: Development of Repast Simphony
We hope to continue these projects throughout the year,
and as part of GSoC 2012 (should GSoC grant us support for interns again).

# CSCS Google Summer of Code 2012 #

For Google Summer of Code 2012 projects, CSCS / CAS2 would like to find students interested in working on one of the following projects.
  * Project #1: Create Agent Based Model _"Demos"_ using Repast Simphony
  * Project #2: Development of Repast Simphony
We anticipate we could mentor and effectively use 6-8 students. However, if you have a different project you think are related to complex systems research, please feel free to contact us, or better, submit a proposal via SoC to CSCS. If it is a project we feel we can mentor and one that is related to complex systems research, we will consider it. For example, see [our past GSOC Ideas pages](http://www.cscs.umich.edu/~rlr/SoC/index.php?n=Main.HomePage#PastGSOCIdeaPages) -- some of those projects we are still pursuing. If you have questions about these projects, please contact the lead mentor, Rick.Riolo@gmail.com, Director of the CSCS Computing Lab.

Some information you should include in your application is as follows:

<ul>
<li>Information about the skills you bring to the project. (classes taken, programming languages you know, 2.) past projects, etc.);</li>
<li>What skills you would like to gain or improve while working on this project; and</li>
<li>What interests you about the project(s), including how the project(s) fit in with your other interests and goals, including goals related to Complex Systems research.</li>
</ul>

Note that we prefer to work with students who will continue to work on the projects they start on in GSoC or other related projects, so include information relevant to that issue as well.

Please read the <a href='http://code.google.com/p/cscs-repast-demos/wiki/ApplicationFAQ'>Application FAQ</a> regarding required and desired experience.

<b>Project 1 Create Agent-based Demonstration and Tutorial Models using Repast Simphony:</b>

<a href='http://repast.sourceforge.net/repast_simphony.html'>Repast Simphony 2.0</a> is a tightly integrated, richly interactive, cross platform Java-based modeling system that runs under Microsoft Windows, Apple Mac OS X, and Linux. It supports the development of extremely flexible models of interacting agents for use on workstations and small computing clusters. Repast Simphony models can be developed in several different forms including the ReLogo dialect of Logo, point-and-click flowcharts, Groovy, or Java, all of which can be fluidly interleaved. NetLogo models can also be imported. Repast Simphony has been successfully used in many application domains including social science, consumer products, supply chains, possible future hydrogen infrastructures, and ancient pedestrian traffic to name a few. For examples see the Repast Simphony <a href='http://repast.sourceforge.net/papers.html'>papers page</a>.

One area of development that needs work is extending the library of demonstration models. The original release of Repast Simphony had a  limited set of models, including some classics such as the Schelling "Tipping" segregation model and a simple version of the Sugarscape model. However, those models were fairly bare-bones and had limited documentation.

A number of new models, tutorials and documentation were created by the GSoC-2011 student developers; most of them are listed on
the [Student Demos List page for 2011](http://code.google.com/p/cscs-repast-demos/wiki/StudentsDemosList).  Many of those were added to the curated [repast-demos site](http://code.google.com/p/repast-demos/) and distributed as part of Repast Simphony 2.0.

However, there still is a need for many more well-documented demonstration models. For example the [Model Libraries with Netlogo](http://ccl.northwestern.edu/netlogo/models/), another ABM platform has many dozen's of models, most well documented and thus useful for people trying to see what Netlogo can do and how to do it.  Repast needs a similarly large and diverse set of demo models.

In addition, some of the existing Repast demo models
could be improved by having more basic documentation and by including more features to show how to use more Repast Simphony's built-in and add-on tools. Additional tutorials would be useful as well.

Having a rich, wide variety of well-documented demo models will:

<ul>
<li>Provide demonstrations that can introduce users to the amazing behaviors of complex adaptive system models of a wide variety of chemical, biological, social and engineered systems;</li>
<li>Give new users a varied and engaging sample of the range of features and look-and-feel of Repast Simphony;</li>
<li>Serve as starting points for users to build their own models, either by extending the demos or just using them as sources of ideas, program fragments, etc.;</li>
<li>"Exercise" Repast Simphony (i.e., serve to test existing features and to discover holes or features that could be added to make writing agent models easier)</li>
</ul>


Thus we are looking for students to work on implementing demonstration and tutorial agent-based models in Repast Simphony using Java, ReLogo, Groovy, or flow charts. In general, the models students propose to build  should be relatively simple, easy to describe and implement. Very large, detailed simulations are too difficult to implement, as well as difficult to explain to new users so they can appreciate and understand how they work and what they do.

We expect each student to develop 6-10 demo-models over the course of the summer. Note that developing a model includes creating adequate documentation, both internal to the program and user-introductory documentation.

We also anticipate that in the course of developing new agent-based models, students will discover holes in the Repast feature set (i.e., a feature that would be useful for implementing various ABMs like the example). The student could then, time and interest permitting, work to implement that feature in RepastS itself (see Project #2, below).


One demonstration model, or set of models, that would be great to have and that is likely to discover holes is a series of models that begin with a simple ReLogo model and works up to large sophisticated parameter sweeps using a version of that model. Such a series of models would involve exercising two relatively recent additions to Repast Simphony, namely ReLogo and the new distributed batch-run component mentioned below in Project #2.

The students will be mentored by Repast Simphony developers and agent-based modelers at the <a href='http://cscs.umich.edu/'>Center for the Study of Complex Systems</a> at the <a href='http://www.umich.edu/'>University of Michigan</a> and at the <a href='http://www.cas.anl.gov/'>Center for Complex Adaptive Agent Systems Simulation</a> (CAS2) in the <a href='http://www.dis.anl.gov/'>Decision and Information Sciences Division</a> at <a href='http://www.anl.gov/'>Argonne National Laboratory</a>.

More information about Repast Simphony can be found in the <a href='http://repast.sourceforge.net/docs.html'>documentation pages</a>. The primary means of obtaining help with Repast is the Repast Interest <a href='https://lists.sourceforge.net/lists/listinfo/repast-interest'>mailing list and the list archives</a>.

<b>Project 2 Development of Repast Simphony:</b>

While our main need is for students to work on implementing demonstration models, students also may work on Repast Simphony itself. Some potential areas include:

<ul>
<li>As mentioned above, students may implement new features in RepastS that serve to fill holes (features that would be very useful but that are missing) they discover in the course of implementing demonstration models. We anticipate this will be the best route for students to get involved in working on Repast Simphony itself.</li>
<li>Repast Simphony has a new distributed batch-run capability. Students could work with this, identify and fill any deficiencies and expand the optimization framework for large scale parameter sweeps.</li>
<li>Create tools support the distribution of single model runs over a cluster using terracotta or a similar tool.</li>
</ul>

The main challenge to working on Repast Simphony  itself is that it may be difficult to find tasks that can be completed in few weeks or so, especially given the need to become familiar with RepastS features, architecture and components. Thus we suspect the best way to do this may be through first building some models to learn about Repast Simphony  as it is, and to learn what would be useful to have that would be of modest scale or at least can be divided into components of modest scale.

<b>Repast License:</b>

The Repast suite software and documentation is licensed under a "New BSD" style license. Please note that Repast Simphony uses a variety of tools and third party external libraries each having its own compatible license, including software released under the Eclipse Public License, the Common Public License, the GNU Library General Public License and other licenses.

<b>Google Group</b>

To track this project please ask to join the following Google Group:
<ul>
<li>cscs-repast-gsoc@googlegroups.com</li>
<li><a href='http://groups.google.com/group/cscs-repast-gsoc?hl=en'>http://groups.google.com/group/cscs-repast-gsoc?hl=en</a></li>
</ul>