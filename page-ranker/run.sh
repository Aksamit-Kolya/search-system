#!/usr/bin/env bash
hadoop jar ./simple-giraph-project-fatjar.jar org.apache.giraph.GiraphRunner \
ru.simple.giraph.project.org.ComputeDegree \
-vif org.apache.giraph.io.formats.IntIntNullTextVertexInputFormat \
-vip example_graph.txt \
-vof org.apache.giraph.io.formats.IdWithValueTextOutputFormat \
-op degrees \
-ca mapreduce.jobtracker.address=localhost:8040 \
-w 1