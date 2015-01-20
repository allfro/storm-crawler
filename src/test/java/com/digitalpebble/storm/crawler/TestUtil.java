/**
 * Licensed to DigitalPebble Ltd under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * DigitalPebble licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.digitalpebble.storm.crawler;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import backtype.storm.metric.api.IMetric;
import backtype.storm.task.TopologyContext;
import backtype.storm.tuple.Tuple;

public class TestUtil {
    public static TopologyContext getMockedTopologyContext() {
        TopologyContext context = mock(TopologyContext.class);
        when(context.registerMetric(anyString(), any(IMetric.class), anyInt())).thenAnswer(
                new Answer<IMetric>() {

                    @Override
                    public IMetric answer(InvocationOnMock invocation) throws Throwable {
                        return invocation.getArgumentAt(1, IMetric.class);
                    }
                });
        return context;
    }

    public static Tuple getMockedTestTuple(String url, String content,
            Map<String, String[]> metadata) {
        Tuple tuple = mock(Tuple.class);
        when(tuple.getStringByField("url")).thenReturn(url);
        when(tuple.getBinaryByField("content")).thenReturn(content.getBytes());
        if (metadata == null) {
            when(tuple.contains("metadata")).thenReturn(Boolean.FALSE);
        } else {
            when(tuple.contains("metadata")).thenReturn(Boolean.TRUE);
            when(tuple.getValueByField("metadata")).thenReturn(metadata);
        }
        return tuple;
    }
}
