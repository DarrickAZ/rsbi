package com.ruisitech.bi.aop;

import com.ruisi.ext.engine.dao.DaoRsbiHelperImpl;
import com.ruisitech.bi.entity.common.BaseEntity;
import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 绑定tid到线程
 */
public class TidAspect {
	private Logger LOGGER = LoggerFactory.getLogger(TidAspect.class);

	public void before(JoinPoint joinPoint){
		System.out.println("_______________________");
		//获取请求入参
		Object[] args = joinPoint.getArgs();
		if(args!=null && args.length>0){
			BaseEntity data = (BaseEntity) args[0];
			//绑定tid到线程
			if(data!=null){
				LOGGER.info(" Tid >>> "+data);
				DaoRsbiHelperImpl.getDaoRsbiThreadLocal().set(data.getTid()!=null?data.getTid()+"":"");
			}
		}
	}
}
