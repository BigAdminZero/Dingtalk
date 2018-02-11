package com.dingtalk.aop;




import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.dingtalk.bean.Department;



@Component("Annotation")
@Aspect
public class Annotation {
	@Pointcut("execution(* *.getDepartment(..))")
	public void getDepartments(){}
	
	@Around("getDepartments()")
    public List<Department> sayAround(ProceedingJoinPoint pjp) throws Throwable{
		return (List<Department>) pjp.proceed();
    }
}
