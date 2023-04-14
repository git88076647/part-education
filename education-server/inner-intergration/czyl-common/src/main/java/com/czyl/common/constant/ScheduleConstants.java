package com.czyl.common.constant;

/**
 * 任务调度通用常量
 * 
 * @author tanghx
 */
public interface ScheduleConstants {
	/** 构建任务Key 预防重复*/
	public static final String JOB_CLASS_NAME = "JOB_CLASS_NAME__";

	public static final String JOB_PROPERTIES = "JOB_PROPERTIES__";

	/** 默认 <br/>
	 * 默认就是立即执行一次
	 * */
	public static final String MISFIRE_DEFAULT = "0";

	/** 立即触发执行 <br/>
	 * 以错过的第一个频率时间立刻开始执行 <br/>
	 * 重做错过的所有频率周期<br/>
	 * 当下一次触发频率发生时间大于当前时间以后，按照Interval的依次执行剩下的频率<br/>
	 * 共执行RepeatCount+1次
	 * */
	public static final String MISFIRE_IGNORE_MISFIRES = "1";

	/** 触发一次执行 <br/>
	 * 以当前时间为触发频率立刻触发一次执行，然后按照Cron频率依次执行
	 * */
	public static final String MISFIRE_FIRE_AND_PROCEED = "2";

	/** 不触发立即执行 <br/>
	 * 等待下次Cron触发频率到达时刻开始按照Cron频率依次执行
	 * */
	public static final String MISFIRE_DO_NOTHING = "3";

	public enum Status {
		/**
		 * 正常
		 */
		NORMAL("0"),
		/**
		 * 暂停
		 */
		PAUSE("1");

		private String value;

		private Status(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}
}
