This is a java version for "https://scikit-garden.github.io/".

ONLY `predict` method of **Mondrian Forest Regression** model is implemented here.
A quick test for predict:
```
@Test
    public void predictForest() throws IOException {
        FileReader reader = new FileReader("trees.txt");
        BufferedReader br = new BufferedReader(reader);
        String line = br.readLine();
        JSONArray json = new JSONArray(line);
        Forest forest = Util.convertJsonToForest(json);

        Result res = forest.predict(new double[]{19681.841666666667,11003.991666666667,10850.358333333334,53632.225});
        System.out.println(res);

        Result res0 = forest.getTrees().get(0).predict(new double[]{19681.841666666667, 11003.991666666667, 10850.358333333334, 53632.225});
        System.out.println(res0);

    }
```

