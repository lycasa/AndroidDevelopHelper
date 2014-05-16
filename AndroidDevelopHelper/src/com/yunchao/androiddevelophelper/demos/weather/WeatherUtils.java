package com.yunchao.androiddevelophelper.demos.weather;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.ksoap2.transport.ServiceConnection;
import org.ksoap2.transport.ServiceConnectionSE;
import org.xmlpull.v1.XmlPullParserException;

/*参考信息
 * 
 * String(0) 到 String(4)：省份，城市，城市代码，城市图片名称，最后更新时间。
 * String(5) 到 String(11)：当天的 气温，概况，风向和风力，天气趋势开始图片名称(以下称：图标一)，
 * 天气趋势结束图片名称(以下称：图标二)，现在的天气实况，天气和生活指数。String(12) 到 String(16)：
 * 第二天的 气温，概况，风向和风力，图标一，图标二。String(17) 到 String(21)：
 * 第三天的 气温，概况，风向和风力，图标一，图标二。String(22) 被查询的城市或地区的介绍
 */

/*实际返回数据
 * 
 * anyType{string=广东; string=梅州; string=59117; string=59117.jpg; string=2013-5-30 9:47:57; 
 * string=25℃/35℃; string=5月30日 晴转多云; string=无持续风向微风; string=0.gif; string=1.gif; 
 * string=今日天气实况：气温：29℃；风向/风力：南风 2级；湿度：69%；空气质量：暂无；紫外线强度：暂无; 
 * string=穿衣指数：暂无。。。（还有各种指数）。。紫外线指数：暂无。; 
 * string=25℃/35℃; string=5月31日 晴; string=无持续风向微风; string=0.gif; string=0.gif; 
 * string=25℃/35℃; string=6月1日 晴; string=无持续风向微风; string=0.gif; string=0.gif; 
 * string=梅州市位于广东省东北部，东北邻福建省的武平、上杭、永定、平和4县，西北接江西省寻乌县，
 * 西面连广东省河源市的龙川县、东源县、紫金县，西南、南面与汕尾市的陆河县、揭阳市的榕城区、揭西县相接，
 * 东南面和潮州市郊区、饶平县相连。全境地理座标位于东经115。18＇至116。56＇、北纬23。23＇至24。56＇之间，
 * 全市总面积15836平方公里。梅州市属亚热带季风气候区，是南亚热带和中亚热带气候区的过渡地带。平远、
 * 蕉岭和梅县北部为中亚热带气候区南缘，五华、丰顺、兴宁、大埔和平远、蕉岭、梅县南部为南亚热带气候区。
 * 这种地处低纬，近临南海、太平洋和山地的特定地形影响，形成夏日长、冬日短，气温高、冷势悬殊、光照充足、
 * 气流闭塞、雨水丰盈且集中的气候。梅州有较多的名胜古迹和人文景观。始建于唐代的千年古刹灵光寺、阴那山五指峰、
 * 松口元魁塔、泮坑瀑布、蕉岭长潭一线天、兴宁合水水库、神光山、大埔丰溪自然保护区、平远南台山、五指石、五华益塘水库、
 * 丰顺温泉及千佛塔等景点，均以其雄、奇、幽、秀之特色吸引许多游客。历史名流文天祥、祝枝山、韩愈等在这些名胜留下足迹。; }
 */

public class WeatherUtils {
	// 通过WebXml获取天气预报所需要的参数以及实现和解析的方法.
	/** 命名空间 */
	private static final String NAMESPACE = "http://WebXml.com.cn/";
	/** 获取天气的WebServiceURL */
	private static String URL = "http://www.webxml.com.cn/webservices/weatherwebservice.asmx";
	/** 调用方法 */
	private static final String METHOD_NAME = "getWeatherbyCityName";
	/** 命名空间+方法名 */
	private static String SOAP_ACTION = "http://WebXml.com.cn/getWeatherbyCityName";// 通过城市名字获取天气

	/**
	 * 获得天气的详细信息
	 */
	public static String[] getWeather(String cityName) throws IOException,
			XmlPullParserException {
		// 保存获取到的信息
		SoapObject detail = null;

		// 1.实例化SoapObject对象
		SoapObject soapObject = new SoapObject(NAMESPACE, METHOD_NAME);
		// 2.如果方法需要参数，设置参数
		soapObject.addProperty("theCityName", cityName);

		// 3.设置Soap的请求信息,获得序列化envelope,参数部分为Soap协议的版本号
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.bodyOut = soapObject;
		envelope.dotNet = true;
		envelope.setOutputSoapObject(soapObject);

		// 4.构建传输对象
		int timeout = 20000;// 设置超时为20秒。即如果没有网络或者网络不好时，超过10秒则会出异常
		MyAndroidHttpTransport httpTransportSE = new MyAndroidHttpTransport(URL, timeout);
		httpTransportSE.debug = true;
		// 5.访问WebService,第一个参数为命名空间 + 方法名,第二个参数为Envelope对象
		httpTransportSE.call(SOAP_ACTION, envelope);

		detail = (SoapObject) envelope.getResponse();// 获取详细天气信息
		if (detail != null) {// 当前城市有天气信息
			return parseWeather(detail);//解析天气
		}
		return null;
	}
	
	/**解析天气，将SoapObject数据转化为String[]*/
	private static String[] parseWeather(SoapObject detail)
			throws UnsupportedEncodingException {
		String[] weather = new String[23];// 一共有23组数据
		for (int i = 0; i < 23; i++) {
			weather[i] = detail.getPropertyAsString(i);
		}
		return weather;
	}
}

//继承HttpTransportSE，使得超时设置有效
class MyAndroidHttpTransport extends HttpTransportSE {
	private int timeout = 20000; // 默认超时时间为20s

	public MyAndroidHttpTransport(String url) {
		super(url);
	}

	public MyAndroidHttpTransport(String url, int timeout) {
		super(url);
		this.timeout = timeout;
	}

	// 此方法使得超时生效
	public ServiceConnection getServiceConnection() throws IOException {
		ServiceConnectionSE serviceConnection = new ServiceConnectionSE(this.url,
				timeout);
		return serviceConnection;
	}
}
