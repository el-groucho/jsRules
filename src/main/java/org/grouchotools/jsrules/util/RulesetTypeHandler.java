package org.grouchotools.jsrules.util;

import org.grouchotools.jsrules.Executor;
import org.grouchotools.jsrules.RulesetExecutor;
import org.grouchotools.jsrules.impl.AllTrueRulesetExecutorImpl;
import org.grouchotools.jsrules.impl.AllTrueRulesetListExecutorImpl;
import org.grouchotools.jsrules.impl.FirstTrueRulesetExecutorImpl;
import org.grouchotools.jsrules.impl.FirstTrueRulesetListExecutorImpl;

import java.util.List;

/**
 * Created by Paul Richardson 5/14/2015
 */
public enum RulesetTypeHandler {
    ALLTRUE {
        @Override
        @SuppressWarnings("unchecked")
        public RulesetExecutor getRulesetExecutor(List<Executor> ruleSet, Object response) {
            return new AllTrueRulesetExecutorImpl(ruleSet, response);
        }
    },
    FIRSTTRUE {
        @Override
        @SuppressWarnings("unchecked")
        public RulesetExecutor getRulesetExecutor(List<Executor> ruleSet, Object response) {
            return new FirstTrueRulesetExecutorImpl(ruleSet);
        }
    },
    ALLTRUELIST {
        @Override
        @SuppressWarnings("unchecked")
        public RulesetExecutor getRulesetExecutor(List<Executor> ruleSet, Object response) {
            return new AllTrueRulesetListExecutorImpl(ruleSet, response);
        }
    },
    FIRSTTRUELIST {
        @Override
        @SuppressWarnings("unchecked")
        public RulesetExecutor getRulesetExecutor(List<Executor> ruleSet, Object response) {
            return new FirstTrueRulesetListExecutorImpl(ruleSet);
        }
    };

    public abstract RulesetExecutor getRulesetExecutor(List<Executor> ruleSet, Object response);
}
