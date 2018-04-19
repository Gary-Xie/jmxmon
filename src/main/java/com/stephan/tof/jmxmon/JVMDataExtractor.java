package com.stephan.tof.jmxmon;

import java.io.IOException;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.MemoryMXBean;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;
import java.util.Collection;

import com.stephan.tof.jmxmon.jmxutil.MemoryPoolProxy;
import com.stephan.tof.jmxmon.jmxutil.ProxyClient;

public abstract class JVMDataExtractor<T> extends JMXCall<T> {

	private final Collection<GarbageCollectorMXBean> gcMXBeanList;
	
	private final RuntimeMXBean runtimeMXBean;
	
	private final Collection<MemoryPoolProxy> memoryPoolList;
	
	private final ThreadMXBean threadMXBean;
	
	private final MemoryMXBean memoryMXBean;
	
	public JVMDataExtractor(ProxyClient proxyClient, int jmxPort) throws IOException {
		super(proxyClient, jmxPort);
		gcMXBeanList = proxyClient.getGarbageCollectorMXBeans();
		runtimeMXBean = proxyClient.getRuntimeMXBean();
		memoryPoolList = proxyClient.getMemoryPoolProxies();
		threadMXBean = proxyClient.getThreadMXBean();
		memoryMXBean = proxyClient.getMemoryMXBean();
	}

	public MemoryMXBean getMemoryMXBean() {
		return memoryMXBean;
	}

	/**
	 * @return the gcMXBeanList
	 */
	public Collection<GarbageCollectorMXBean> getGcMXBeanList() {
		return gcMXBeanList;
	}

	/**
	 * @return the runtimeMXBean
	 */
	public RuntimeMXBean getRuntimeMXBean() {
		return runtimeMXBean;
	}

	/**
	 * @return the memoryPool
	 */
	public Collection<MemoryPoolProxy> getMemoryPoolList() {
		return memoryPoolList;
	}

	/**
	 * @return the threadMXBean
	 */
	public ThreadMXBean getThreadMXBean() {
		return threadMXBean;
	}
	
}
