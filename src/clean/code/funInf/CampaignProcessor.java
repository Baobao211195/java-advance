package clean.code.funInf;

import clean.code.lamda.Apple;

@FunctionalInterface
public interface CampaignProcessor {
    
    Apple process(Apple apple) throws Exception;
}