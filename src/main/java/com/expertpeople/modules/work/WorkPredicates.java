//package com.expertpeople.modules.work;
//
//
//import com.querydsl.core.types.Predicate;
//public class WorkPredicates {
//    public static Predicate findByKeyword(String keyword){
//        QWork qWork=QWork.work;
//
//        return qWork.published.isTrue().and(qWork.jobs.any().job.containsIgnoreCase(keyword))
//                .or(qWork.zones.any().localNameOfCity.containsIgnoreCase(keyword))
//                .or(qWork.title.containsIgnoreCase(keyword));
//    }
//}
