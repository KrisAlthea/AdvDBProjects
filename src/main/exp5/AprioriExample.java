package main.exp5;

import weka.associations.Apriori;
import weka.associations.AssociationRule;
import weka.associations.AssociationRules;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class AprioriExample {
    public static void main(String[] args) throws Exception {
        // 加载数据集
        DataSource dataSource = new DataSource("path_to_your_dataset.arff");
        Instances data = dataSource.getDataSet();

        // 初始化Apriori
        Apriori apriori = new Apriori();
        apriori.setClassIndex(data.classIndex());
        apriori.buildAssociations(data);

        // 获取关联规则
        AssociationRules rules = apriori.getAssociationRules();
        for (AssociationRule rule : rules.getRules()) {
            System.out.println(rule);
        }
    }
}
