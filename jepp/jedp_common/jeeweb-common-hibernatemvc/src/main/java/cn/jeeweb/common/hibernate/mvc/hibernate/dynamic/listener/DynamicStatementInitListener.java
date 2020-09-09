package cn.jeeweb.common.hibernate.mvc.hibernate.dynamic.listener;

import cn.jeeweb.common.utils.SpringContextHolder;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import  cn.jeeweb.common.hibernate.mvc.hibernate.dynamic.builder.DynamicHibernateStatementBuilder;
import  cn.jeeweb.common.hibernate.mvc.hibernate.dynamic.builder.NoneDynamicHibernateStatementBuilder;

public class DynamicStatementInitListener implements ApplicationListener<ContextRefreshedEvent> {

	protected DynamicHibernateStatementBuilder dynamicStatementBuilder = SpringContextHolder.getApplicationContext()
			.getBean(DynamicHibernateStatementBuilder.class);

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		try {
			if (this.dynamicStatementBuilder == null) {
				this.dynamicStatementBuilder = new NoneDynamicHibernateStatementBuilder();
			}
			dynamicStatementBuilder.init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}