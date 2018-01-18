package baidumap;

public class Test {
    public static void main(String[] args) throws Exception {
        String result = HttpUtil.get("http://api.map.baidu.com/geocoder?location=39.7956245456456,116.571793345345345345345&output=json&key=YhSNWltxRLcfPY5s4cU7W44u");
        System.out.println(result);
    }
}
