# jsRules
Simple JSON-based Rules Engine

## Introduction

jsRules is a "no frills" rules engine that allows for quick and easy rules resolution all configurable through JSON 
files.

## Why a rules engine?

A rules engine seperates your business logic from your application logic, making it possible to changes those rules
without updating code or even redeploying your app. jsRules uses a straight-forward JSON implementation that could
easily be edited via a web page, mobile app, or even by hand...keeping those rules in the hands of the people who
understand them, without having to engage developers for simple changes. 

## How do I implement it?

1. Import the JAR into any Java application
2. Create your rules as JSON and put them on the classpath.
3. Put the parameters needed to resolve the rules into a Map<String, Object>
4. Finally, call the rules engine using just one line of code:


    JsRules.getInstance().execute("DrinkingAge");
       
## Terminology

+ **Rule** -- a rule is the building block for the rules engine. It performs very simple calculations using a left
parameter, a right parameter, and an operator.
+ **Ruleset** -- a ruleset is a set of rules executed in order. This is what is invoked via the JsRules execute() 
method.
+ **Ruleset List** -- a ruleset list is a set of rulesets executed in order. Ruleset Lists are also a type of ruleset,
so you can stack them on top of each other in any configuration needed, allowing your rules to be as simple or complex
as necessary.

## Rules

Rules consist of the following components:

+ **Rule Name** -- a unique identifier for the rule. It should match the name of the json file containing it.
+ **Left Parameter** -- the name and class of the left parameter.
+ **Operator** -- denotes the operation to be performed between the left parameter and the right parameter.
+ **Right Parameter** -- the name and class of the right parameter. Optionally, may contain a static value that will
always be used for comparison.
+ **Response** -- the value and class of the response. This will be returned if the rule evaluates as true. Otherwise,
null will be returned.

### Classes

The following classes are currently available for use in both the parameters and the response:

+ **Boolean**
+ **Double**
+ **Long**
+ **String**
+ **NumberSet**
+ **DateTime** -- backed by Joda Time, expected format "YYYY-MM-ddTHH:mm:ss.SSS-ZZ:ZZ"

### Operators

The following operators are available for use in rules:

+ **GT** -- greater than (works for numbers or dates only)
+ **LT** -- less than (works for numbers or dates only)
+ **GTE** -- greater than or equal (works for numbers or dates only)
+ **LTE** -- less than or equal (works for numbers or dates only)
+ **EQ** -- equals
+ **NE** -- not equals
+ **IN** -- in (left parameter is a number or date, right parameter is a set of numbers or dates)
+ **NOT_IN** -- not in (inclusive: left parameter is a number or date, right parameter is a set of numbers or dates)
+ **BETWEEN** -- between (inclusive: left parameter is a number or date, right parameter is a set of two numbers or dates)
+ **NOT_BETWEEN** -- not between (left parameter is a number or date, right parameter is a set of two numbers or dates)

### Sample Rule json

This rule is named "GreaterThan10" and evaluates whether the left parameter is greater than 10. If the rule evaluates
true, it will return a Boolean set to true.

    {
      "ruleName": "GreaterThan10",
      "leftParamConfig": {
        "parameterName": "left",
        "parameterClass": "Long"
      },
      "operator": "GT",
      "rightParamConfig": {
        "parameterName": "right",
        "parameterClass": "Long",
        "parameterStaticValue": "10"
      },
      "responseConfig": {
        "response": "true",
        "responseClass": "Boolean"
      }
    }

## Rulesets

A ruleset evaluates several rules in sequence, and consists of the following components:

+ **Ruleset Name** -- a unique identifier for the ruleset. It should match the name of the json file containing it.
+ **Ruleset Type** -- the type of ruleset we are executing ("AllTrue" or "FirstTrue")
+ **Response** -- the value and class of the response that will be sent if the ruleset evaluates as true (not applicable 
to "FirstTrue" type)
+ **Components** -- a list of rules to be executed, by name, in the order they should be executed

### Ruleset Types

+ **AllTrue** -- if all the rules evaluate as true, returns the given response
+ **FirstTrue** -- executes all the rules in order, and returns the response of the first one that evaluates as true

### Sample Ruleset Json

    {
      "rulesetName": "InventoryRuleset",
      "rulesetType": "AllTrue",
      "responseConfig": {
        "response": "Item is in stock",
        "responseClass": "String"
      },
      "components": [
        "ItemIsCarriedRule",
        "MoreThanZeroStocked"
      ]
    }
    
## Ruleset List

A ruleset list is a type of ruleset that executes a series of rulesets in order (as opposed to a series of rules). Its
format is exactly the same as any other ruleset, the only difference is the type and the components.

### Ruleset List Types

+ **AllTrueList** -- if all the rulesets evaluate as true, returns the given response
+ **FirstTrueList** -- executes all the rulesets in order, and returns the response of the first one that evaluates as 
true

### Sample Ruleset List Json

    {
      "rulesetName": "CanPurchaseRulesetList",
      "rulesetType": "AllTrueList",
      "responseConfig": {
        "response": "Customer can purchase something",
        "responseClass": "String"
      },
      "components": [
        "InBudgetRuleset",
        "InventoryRuleset"
      ]
    }
    
## Future Features

This library is very much in its infancy. Here are some features under consideration for future releases:

+ **Ruleset Chaining** -- add the response from one ruleset to the parameters so that it can be used in downstream rule
execution
+ **Term Resolving** -- add the ability to resolve parameters that are required by not provided
+ **Improved Caching Options**
+ **NoSQL Integration**
+ **More Classes**
+ **More Operators**
+ **Custom Classes and Operators**
+ **And More!**