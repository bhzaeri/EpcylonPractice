package epcylon;

public class Util {

	public static String[] lines = new String[] {
			"{\"quote\":{\"data\":{\"ask\":1.3018,\"bid\":1.30099,\"last\":1.30099},\"pair\":\"USD-CAD\",\"time\":\"2016-04-01T01:20:47.270Z\"}}",
			"{\"quote\":{\"data\":{\"ask\":1.3018,\"bid\":1.30099,\"last\":1.30099},\"pair\":\"USD-CAD\",\"time\":\"2016-04-01T01:23:49.150Z\"}}",
			"{\"quote\":{\"data\":{\"ask\":1.3018,\"bid\":1.30099,\"last\":1.30099},\"pair\":\"USD-CAD\",\"time\":\"2016-04-01T01:24:49.520Z\"}}",
			"{\"quote\":{\"data\":{\"ask\":1.3018,\"bid\":1.30099,\"last\":1.30099},\"pair\":\"USD-CAD\",\"time\":\"2016-04-01T01:26:50.020Z\"}}",
			"{\"quote\":{\"data\":{\"ask\":1.3018,\"bid\":1.30099,\"last\":1.30099},\"pair\":\"USD-CAD\",\"time\":\"2016-04-01T01:27:51.400Z\"}}",
			"{\"quote\":{\"data\":{\"ask\":1.3018,\"bid\":1.30099,\"last\":1.30099},\"pair\":\"USD-CAD\",\"time\":\"2016-04-01T01:28:51.770Z\"}}",
			"{\"quote\":{\"data\":{\"ask\":1.3018,\"bid\":1.30099,\"last\":1.30099},\"pair\":\"USD-CAD\",\"time\":\"2016-04-01T01:29:52.150Z\"}}",
			"{\"quote\":{\"data\":{\"ask\":1.3018,\"bid\":1.30099,\"last\":1.30099},\"pair\":\"USD-CAD\",\"time\":\"2016-04-01T01:41:52.520Z\"}}",
			"{\"quote\":{\"data\":{\"ask\":1.3018,\"bid\":1.30099,\"last\":1.30099},\"pair\":\"USD-CAD\",\"time\":\"2016-04-01T02:33:06.020Z\"}}",
			"{\"quote\":{\"data\":{\"ask\":1.3018,\"bid\":1.30099,\"last\":1.30099},\"pair\":\"USD-CAD\",\"time\":\"2016-04-01T03:03:21.403Z\"}}",

			"{\"quote\":{\"data\":{\"ask\":1.3018,\"bid\":1.30099,\"last\":1.30099},\"pair\":\"USD-CAD\",\"time\":\"2016-04-01T03:04:31.403Z\"}}",
			"{\"quote\":{\"data\":{\"ask\":1.3018,\"bid\":1.30099,\"last\":1.30099},\"pair\":\"USD-CAD\",\"time\":\"2016-04-01T03:05:21.403Z\"}}",
			"{\"quote\":{\"data\":{\"ask\":1.3018,\"bid\":1.30099,\"last\":1.30099},\"pair\":\"USD-CAD\",\"time\":\"2016-04-01T03:06:21.403Z\"}}",
			"{\"quote\":{\"data\":{\"ask\":1.3018,\"bid\":1.30099,\"last\":1.30099},\"pair\":\"USD-CAD\",\"time\":\"2016-04-01T03:07:21.403Z\"}}",
			"{\"quote\":{\"data\":{\"ask\":1.3018,\"bid\":1.30099,\"last\":1.30099},\"pair\":\"USD-CAD\",\"time\":\"2016-04-01T03:08:21.403Z\"}}",
			"{\"quote\":{\"data\":{\"ask\":1.3018,\"bid\":1.30099,\"last\":1.30099},\"pair\":\"USD-CAD\",\"time\":\"2016-04-01T03:09:21.403Z\"}}",
			"{\"quote\":{\"data\":{\"ask\":1.3018,\"bid\":1.30099,\"last\":1.30099},\"pair\":\"USD-CAD\",\"time\":\"2016-04-01T03:10:21.403Z\"}}",
			"{\"quote\":{\"data\":{\"ask\":1.3018,\"bid\":1.30099,\"last\":1.30099},\"pair\":\"USD-CAD\",\"time\":\"2016-04-01T03:11:21.403Z\"}}",
			"{\"quote\":{\"data\":{\"ask\":1.3018,\"bid\":1.30099,\"last\":1.30099},\"pair\":\"USD-CAD\",\"time\":\"2016-04-01T03:12:21.403Z\"}}",
			"{\"quote\":{\"data\":{\"ask\":1.3018,\"bid\":1.30099,\"last\":1.30099},\"pair\":\"USD-CAD\",\"time\":\"2016-04-01T03:13:21.403Z\"}}",

			"{\"quote\":{\"data\":{\"ask\":1.3018,\"bid\":1.30099,\"last\":1.30099},\"pair\":\"USD-CAD\",\"time\":\"2016-04-01T04:04:31.403Z\"}}",
			"{\"quote\":{\"data\":{\"ask\":1.3018,\"bid\":1.30099,\"last\":1.30099},\"pair\":\"USD-CAD\",\"time\":\"2016-04-01T04:05:21.403Z\"}}",
			"{\"quote\":{\"data\":{\"ask\":1.3018,\"bid\":1.30099,\"last\":1.30099},\"pair\":\"USD-CAD\",\"time\":\"2016-04-01T04:06:21.403Z\"}}",
			"{\"quote\":{\"data\":{\"ask\":1.3018,\"bid\":1.30099,\"last\":1.30099},\"pair\":\"USD-CAD\",\"time\":\"2016-04-01T04:07:21.403Z\"}}",
			"{\"quote\":{\"data\":{\"ask\":1.3018,\"bid\":1.30099,\"last\":1.30099},\"pair\":\"USD-CAD\",\"time\":\"2016-04-01T04:08:21.403Z\"}}",
			"{\"quote\":{\"data\":{\"ask\":1.3018,\"bid\":1.30099,\"last\":1.30099},\"pair\":\"USD-CAD\",\"time\":\"2016-04-01T04:09:21.403Z\"}}",
			"{\"quote\":{\"data\":{\"ask\":1.3018,\"bid\":1.30099,\"last\":1.30099},\"pair\":\"USD-CAD\",\"time\":\"2016-04-01T04:10:21.403Z\"}}",
			"{\"quote\":{\"data\":{\"ask\":1.3018,\"bid\":1.30099,\"last\":1.30099},\"pair\":\"USD-CAD\",\"time\":\"2016-04-01T04:11:21.403Z\"}}",
			"{\"quote\":{\"data\":{\"ask\":1.3018,\"bid\":1.30099,\"last\":1.30099},\"pair\":\"USD-CAD\",\"time\":\"2016-04-01T04:12:21.403Z\"}}",
			"{\"quote\":{\"data\":{\"ask\":1.3018,\"bid\":1.30099,\"last\":1.30099},\"pair\":\"USD-CAD\",\"time\":\"2016-04-01T04:13:21.403Z\"}}",

			"{\"quote\":{\"data\":{\"ask\":1.3018,\"bid\":1.30099,\"last\":1.30099},\"pair\":\"USD-CAD\",\"time\":\"2016-04-01T05:04:31.403Z\"}}",
			"{\"quote\":{\"data\":{\"ask\":1.3018,\"bid\":1.30099,\"last\":1.30099},\"pair\":\"USD-CAD\",\"time\":\"2016-04-01T05:05:21.403Z\"}}",
			"{\"quote\":{\"data\":{\"ask\":1.3018,\"bid\":1.30099,\"last\":1.30099},\"pair\":\"USD-CAD\",\"time\":\"2016-04-01T05:06:21.403Z\"}}",
			"{\"quote\":{\"data\":{\"ask\":1.3018,\"bid\":1.30099,\"last\":1.30099},\"pair\":\"USD-CAD\",\"time\":\"2016-04-01T05:07:21.403Z\"}}",
			"{\"quote\":{\"data\":{\"ask\":1.3018,\"bid\":1.30099,\"last\":1.30099},\"pair\":\"USD-CAD\",\"time\":\"2016-04-01T05:08:21.403Z\"}}",
			"{\"quote\":{\"data\":{\"ask\":1.3018,\"bid\":1.30099,\"last\":1.30099},\"pair\":\"USD-CAD\",\"time\":\"2016-04-01T05:09:21.403Z\"}}",
			"{\"quote\":{\"data\":{\"ask\":1.3018,\"bid\":1.30099,\"last\":1.30099},\"pair\":\"USD-CAD\",\"time\":\"2016-04-01T05:10:21.403Z\"}}",
			"{\"quote\":{\"data\":{\"ask\":1.3018,\"bid\":1.30099,\"last\":1.30099},\"pair\":\"USD-CAD\",\"time\":\"2016-04-01T05:11:21.403Z\"}}",
			"{\"quote\":{\"data\":{\"ask\":1.3018,\"bid\":1.30099,\"last\":1.30099},\"pair\":\"USD-CAD\",\"time\":\"2016-04-01T05:12:21.403Z\"}}",
			"{\"quote\":{\"data\":{\"ask\":1.3018,\"bid\":1.30099,\"last\":1.30099},\"pair\":\"USD-CAD\",\"time\":\"2016-04-01T05:13:21.403Z\"}}",

			"{\"quote\":{\"data\":{\"ask\":1.3018,\"bid\":1.30099,\"last\":1.30099},\"pair\":\"USD-CAD\",\"time\":\"2016-04-01T06:04:31.403Z\"}}",
			"{\"quote\":{\"data\":{\"ask\":1.3018,\"bid\":1.30099,\"last\":1.30099},\"pair\":\"USD-CAD\",\"time\":\"2016-04-01T06:05:21.403Z\"}}",
			"{\"quote\":{\"data\":{\"ask\":1.3018,\"bid\":1.30099,\"last\":1.30099},\"pair\":\"USD-CAD\",\"time\":\"2016-04-01T06:06:21.403Z\"}}",
			"{\"quote\":{\"data\":{\"ask\":1.3018,\"bid\":1.30099,\"last\":1.30099},\"pair\":\"USD-CAD\",\"time\":\"2016-04-01T06:07:21.403Z\"}}",
			"{\"quote\":{\"data\":{\"ask\":1.3018,\"bid\":1.30099,\"last\":1.30099},\"pair\":\"USD-CAD\",\"time\":\"2016-04-01T06:08:21.403Z\"}}",
			"{\"quote\":{\"data\":{\"ask\":1.3018,\"bid\":1.30099,\"last\":1.30099},\"pair\":\"USD-CAD\",\"time\":\"2016-04-01T06:09:21.403Z\"}}",
			"{\"quote\":{\"data\":{\"ask\":1.3018,\"bid\":1.30099,\"last\":1.30099},\"pair\":\"USD-CAD\",\"time\":\"2016-04-01T06:10:21.403Z\"}}",
			"{\"quote\":{\"data\":{\"ask\":1.3018,\"bid\":1.30099,\"last\":1.30099},\"pair\":\"USD-CAD\",\"time\":\"2016-04-01T06:11:21.403Z\"}}",
			"{\"quote\":{\"data\":{\"ask\":1.3018,\"bid\":1.30099,\"last\":1.30099},\"pair\":\"USD-CAD\",\"time\":\"2016-04-01T06:12:21.403Z\"}}",
			"{\"quote\":{\"data\":{\"ask\":1.3018,\"bid\":1.30099,\"last\":1.30099},\"pair\":\"USD-CAD\",\"time\":\"2016-04-01T06:13:21.403Z\"}}",

			"{\"quote\":{\"data\":{\"ask\":1.3018,\"bid\":1.30099,\"last\":1.30099},\"pair\":\"USD-CAD\",\"time\":\"2016-04-01T07:04:31.403Z\"}}",
			"{\"quote\":{\"data\":{\"ask\":1.3018,\"bid\":1.30099,\"last\":1.30099},\"pair\":\"USD-CAD\",\"time\":\"2016-04-01T07:05:21.403Z\"}}",
			"{\"quote\":{\"data\":{\"ask\":1.3018,\"bid\":1.30099,\"last\":1.30099},\"pair\":\"USD-CAD\",\"time\":\"2016-04-01T07:06:21.403Z\"}}",
			"{\"quote\":{\"data\":{\"ask\":1.3018,\"bid\":1.30099,\"last\":1.30099},\"pair\":\"USD-CAD\",\"time\":\"2016-04-01T07:07:21.403Z\"}}",
			"{\"quote\":{\"data\":{\"ask\":1.3018,\"bid\":1.30099,\"last\":1.30099},\"pair\":\"USD-CAD\",\"time\":\"2016-04-01T07:08:21.403Z\"}}",
			"{\"quote\":{\"data\":{\"ask\":1.3018,\"bid\":1.30099,\"last\":1.30099},\"pair\":\"USD-CAD\",\"time\":\"2016-04-01T07:09:21.403Z\"}}",
			"{\"quote\":{\"data\":{\"ask\":1.3018,\"bid\":1.30099,\"last\":1.30099},\"pair\":\"USD-CAD\",\"time\":\"2016-04-01T07:10:21.403Z\"}}",
			"{\"quote\":{\"data\":{\"ask\":1.3018,\"bid\":1.30099,\"last\":1.30099},\"pair\":\"USD-CAD\",\"time\":\"2016-04-01T07:11:21.403Z\"}}",
			"{\"quote\":{\"data\":{\"ask\":1.3018,\"bid\":1.30099,\"last\":1.30099},\"pair\":\"USD-CAD\",\"time\":\"2016-04-01T07:12:21.403Z\"}}",
			"{\"quote\":{\"data\":{\"ask\":1.3018,\"bid\":1.30099,\"last\":1.30099},\"pair\":\"USD-CAD\",\"time\":\"2016-04-01T07:13:21.403Z\"}}", };
}
