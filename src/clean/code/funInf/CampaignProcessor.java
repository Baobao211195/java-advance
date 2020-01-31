package clean.code.funInf;

import clean.code.vo.Apple;

@FunctionalInterface
public interface CampaignProcessor {
    
    Apple process(Apple apple) throws Exception;
}
