package com.stephan.tof.jmxmon;

import java.io.IOException;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stephan.tof.jmxmon.Constants.CounterType;
import com.stephan.tof.jmxmon.JVMHeapMemoryUsedExtractor.HeapMemoryUsedInfo;
import com.stephan.tof.jmxmon.bean.FalconItem;
import com.stephan.tof.jmxmon.jmxutil.ProxyClient;


public class JVMHeapMemoryUsedExtractor extends JVMDataExtractor<HeapMemoryUsedInfo> {
        
    private static Logger logger = LoggerFactory.getLogger(JVMHeapMemoryUsedExtractor.class);

    public JVMHeapMemoryUsedExtractor(ProxyClient proxyClient, int jmxPort)
            throws IOException {
        super(proxyClient, jmxPort);
    }

    @Override
    public HeapMemoryUsedInfo call() throws Exception {
        HeapMemoryUsedInfo heapMemoryUsedInfo = null;
        long heapMemoryInitSize = 0L;
        long heapMemoryMaxSize = 0L;
        long heapMemoryUsedSize = 0L;
        
        MemoryMXBean memoryMXBean = getMemoryMXBean();
        if (null != memoryMXBean) {
            logger.info("null != memoryMXBean");
            MemoryUsage memoryUsage = memoryMXBean.getHeapMemoryUsage();
            heapMemoryInitSize = memoryUsage.getInit();
            heapMemoryMaxSize = memoryUsage.getMax();
            heapMemoryUsedSize = memoryUsage.getUsed();
        }

        heapMemoryUsedInfo = new HeapMemoryUsedInfo(heapMemoryInitSize, heapMemoryMaxSize, heapMemoryUsedSize);
        return heapMemoryUsedInfo;
    }

    @Override
    public List<FalconItem> build(HeapMemoryUsedInfo jmxResultData) throws Exception {
        List<FalconItem> items = new ArrayList<FalconItem>();

        FalconItem heapMemoryInitItem = new FalconItem();
        heapMemoryInitItem.setCounterType(CounterType.GAUGE.toString());
        heapMemoryInitItem.setEndpoint(Config.I.getHostname());
        heapMemoryInitItem.setMetric(Constants.heapMemoryInit);
        heapMemoryInitItem.setStep(Constants.defaultStep);
        heapMemoryInitItem.setTags(StringUtils.lowerCase("jmxport=" + getJmxPort()));
        heapMemoryInitItem.setTimestamp(System.currentTimeMillis() / 1000);
        heapMemoryInitItem.setValue(jmxResultData.getHeapMemoryInitSize());
        items.add(heapMemoryInitItem);

        FalconItem heapMemoryMaxItem = new FalconItem();
        heapMemoryMaxItem.setCounterType(CounterType.GAUGE.toString());
        heapMemoryMaxItem.setEndpoint(Config.I.getHostname());
        heapMemoryMaxItem.setMetric(Constants.heapMemoryMax);
        heapMemoryMaxItem.setStep(Constants.defaultStep);
        heapMemoryMaxItem.setTags(StringUtils.lowerCase("jmxport=" + getJmxPort()));
        heapMemoryMaxItem.setTimestamp(System.currentTimeMillis() / 1000);
        heapMemoryMaxItem.setValue(jmxResultData.getHeapMemoryMaxSize());
        items.add(heapMemoryMaxItem);

        FalconItem heapMemoryUsedItem = new FalconItem();
        heapMemoryUsedItem.setCounterType(CounterType.GAUGE.toString());
        heapMemoryUsedItem.setEndpoint(Config.I.getHostname());
        heapMemoryUsedItem.setMetric(Constants.heapMemoryUsed);
        heapMemoryUsedItem.setStep(Constants.defaultStep);
        heapMemoryUsedItem.setTags(StringUtils.lowerCase("jmxport=" + getJmxPort()));
        heapMemoryUsedItem.setTimestamp(System.currentTimeMillis() / 1000);
        heapMemoryUsedItem.setValue(jmxResultData.getHeapMemoryUsedSize());
        items.add(heapMemoryUsedItem);

        return items;
    }

    public class HeapMemoryUsedInfo {
        private final long heapMemoryInitSize;
        
        private final long heapMemoryMaxSize;
        
        private final long heapMemoryUsedSize;

        public HeapMemoryUsedInfo(long heapMemoryInitSize, long heapMemoryMaxSize, long heapMemoryUsedSize) {
            super();
            this.heapMemoryInitSize = heapMemoryInitSize;
            this.heapMemoryMaxSize = heapMemoryMaxSize;
            this.heapMemoryUsedSize = heapMemoryUsedSize;
        }

        public long getHeapMemoryInitSize() {
            return heapMemoryInitSize;
        }

        public long getHeapMemoryMaxSize() {
            return heapMemoryMaxSize;
        }

        public long getHeapMemoryUsedSize() {
            return heapMemoryUsedSize;
        }

    }
}
