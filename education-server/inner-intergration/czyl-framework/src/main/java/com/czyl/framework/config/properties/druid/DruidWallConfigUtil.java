package com.czyl.framework.config.properties.druid;

import com.alibaba.druid.wall.WallConfig;
import com.czyl.common.utils.StringUtils;

/**
 * 防火墙配置工具类 解决打war包外部部署的异常
 *
 * @author tanghx
 */
public final class DruidWallConfigUtil {

	/**
	 * 根据当前的配置和全局的配置生成druid防火墙配置
	 *
	 * @param c 当前配置
	 * @param g 全局配置
	 * @return 防火墙配置
	 */
	public static WallConfig toWallConfig(DruidWallConfig c, DruidWallConfig g) {
		WallConfig wallConfig = new WallConfig();

		String tempDir = StringUtils.isEmpty(c.getConfig().getDir()) ? g.getConfig().getDir() : c.getConfig().getDir();
		if (!StringUtils.isEmpty(tempDir)) {
			wallConfig.loadConfig(tempDir);
		}
		String tempTenantTablePattern = StringUtils.isEmpty(c.getConfig().getTenantTablePattern()) ? g.getConfig().getTenantTablePattern() : c.getConfig().getTenantTablePattern();
		if (!StringUtils.isEmpty(tempTenantTablePattern)) {
			wallConfig.setTenantTablePattern(tempTenantTablePattern);
		}
		String tempTenantColumn = StringUtils.isEmpty(c.getConfig().getTenantColumn()) ? g.getConfig().getTenantColumn() : c.getConfig().getTenantColumn();
		if (!StringUtils.isEmpty(tempTenantColumn)) {
			wallConfig.setTenantTablePattern(tempTenantColumn);
		}
		Boolean tempNoneBaseStatementAllow = c.getConfig().getNoneBaseStatementAllow() == null ? g.getConfig().getNoneBaseStatementAllow() : c.getConfig().getNoneBaseStatementAllow();
		if (tempNoneBaseStatementAllow != null && tempNoneBaseStatementAllow) {
			wallConfig.setNoneBaseStatementAllow(true);
		}
		Integer tempInsertValuesCheckSize = c.getConfig().getInsertValuesCheckSize() == null ? g.getConfig().getInsertValuesCheckSize() : c.getConfig().getInsertValuesCheckSize();
		if (tempInsertValuesCheckSize != null) {
			wallConfig.setInsertValuesCheckSize(tempInsertValuesCheckSize);
		}
		Integer tempSelectLimit = c.getConfig().getSelectLimit() == null ? g.getConfig().getSelectLimit() : c.getConfig().getSelectLimit();
		if (tempSelectLimit != null) {
			c.getConfig().setSelectLimit(tempSelectLimit);
		}

		Boolean tempCallAllow = c.getConfig().getCallAllow() == null ? g.getConfig().getCallAllow() : c.getConfig().getCallAllow();
		if (tempCallAllow != null && !tempCallAllow) {
			wallConfig.setCallAllow(false);
		}
		Boolean tempSelectAllow = c.getConfig().getSelectAllow() == null ? g.getConfig().getSelectAllow() : c.getConfig().getSelectAllow();
		if (tempSelectAllow != null && !tempSelectAllow) {
			wallConfig.setSelelctAllow(false);
		}
		Boolean tempSelectIntoAllow = c.getConfig().getSelectIntoAllow() == null ? g.getConfig().getSelectIntoAllow() : c.getConfig().getSelectIntoAllow();
		if (tempSelectIntoAllow != null && !tempSelectIntoAllow) {
			wallConfig.setSelectIntoAllow(false);
		}
		Boolean tempSelectIntoOutfileAllow = c.getConfig().getSelectIntoOutfileAllow() == null ? g.getConfig().getSelectIntoOutfileAllow() : c.getConfig().getSelectIntoOutfileAllow();
		if (tempSelectIntoOutfileAllow != null && tempSelectIntoOutfileAllow) {
			wallConfig.setSelectIntoOutfileAllow(true);
		}
		Boolean tempSelectWhereAlwayTrueCheck = c.getConfig().getSelectWhereAlwayTrueCheck() == null ? g.getConfig().getSelectWhereAlwayTrueCheck() : c.getConfig().getSelectWhereAlwayTrueCheck();
		if (tempSelectWhereAlwayTrueCheck != null && !tempSelectWhereAlwayTrueCheck) {
			wallConfig.setSelectWhereAlwayTrueCheck(false);
		}
		Boolean tempSelectHavingAlwayTrueCheck = c.getConfig().getSelectHavingAlwayTrueCheck() == null ? g.getConfig().getSelectHavingAlwayTrueCheck() : c.getConfig().getSelectHavingAlwayTrueCheck();
		if (tempSelectHavingAlwayTrueCheck != null && !tempSelectHavingAlwayTrueCheck) {
			wallConfig.setSelectHavingAlwayTrueCheck(false);
		}
		Boolean tempSelectUnionCheck = c.getConfig().getSelectUnionCheck() == null ? g.getConfig().getSelectUnionCheck() : c.getConfig().getSelectUnionCheck();
		if (tempSelectUnionCheck != null && !tempSelectUnionCheck) {
			wallConfig.setSelectUnionCheck(false);
		}
		Boolean tempSelectMinusCheck = c.getConfig().getSelectMinusCheck() == null ? g.getConfig().getSelectMinusCheck() : c.getConfig().getSelectMinusCheck();
		if (tempSelectMinusCheck != null && !tempSelectMinusCheck) {
			wallConfig.setSelectMinusCheck(false);
		}
		Boolean tempSelectExceptCheck = c.getConfig().getSelectExceptCheck() == null ? g.getConfig().getSelectExceptCheck() : c.getConfig().getSelectExceptCheck();
		if (tempSelectExceptCheck != null && !tempSelectExceptCheck) {
			wallConfig.setSelectExceptCheck(false);
		}
		Boolean tempSelectIntersectCheck = c.getConfig().getSelectIntersectCheck() == null ? g.getConfig().getSelectIntersectCheck() : c.getConfig().getSelectIntersectCheck();
		if (tempSelectIntersectCheck != null && !tempSelectIntersectCheck) {
			wallConfig.setSelectIntersectCheck(false);
		}
		Boolean tempCreateTableAllow = c.getConfig().getCreateTableAllow() == null ? g.getConfig().getCreateTableAllow() : c.getConfig().getCreateTableAllow();
		if (tempCreateTableAllow != null && !tempCreateTableAllow) {
			wallConfig.setCreateTableAllow(false);
		}
		Boolean tempDropTableAllow = c.getConfig().getDropTableAllow() == null ? g.getConfig().getDropTableAllow() : c.getConfig().getDropTableAllow();
		if (tempDropTableAllow != null && !tempDropTableAllow) {
			wallConfig.setDropTableAllow(false);
		}
		Boolean tempAlterTableAllow = c.getConfig().getAlterTableAllow() == null ? g.getConfig().getAlterTableAllow() : c.getConfig().getAlterTableAllow();
		if (tempAlterTableAllow != null && !tempAlterTableAllow) {
			wallConfig.setAlterTableAllow(false);
		}
		Boolean tempRenameTableAllow = c.getConfig().getRenameTableAllow() == null ? g.getConfig().getRenameTableAllow() : c.getConfig().getRenameTableAllow();
		if (tempRenameTableAllow != null && !tempRenameTableAllow) {
			wallConfig.setRenameTableAllow(false);
		}
		Boolean tempHintAllow = c.getConfig().getHintAllow() == null ? g.getConfig().getHintAllow() : c.getConfig().getHintAllow();
		if (tempHintAllow != null && !tempHintAllow) {
			wallConfig.setHintAllow(false);
		}
		Boolean tempLockTableAllow = c.getConfig().getLockTableAllow() == null ? g.getConfig().getLockTableAllow() : c.getConfig().getLockTableAllow();
		if (tempLockTableAllow != null && !tempLockTableAllow) {
			wallConfig.setLockTableAllow(false);
		}
		Boolean tempStartTransactionAllow = c.getConfig().getStartTransactionAllow() == null ? g.getConfig().getStartTransactionAllow() : c.getConfig().getStartTransactionAllow();
		if (tempStartTransactionAllow != null && !tempStartTransactionAllow) {
			wallConfig.setStartTransactionAllow(false);
		}
		Boolean tempBlockAllow = c.getConfig().getBlockAllow() == null ? g.getConfig().getBlockAllow() : c.getConfig().getBlockAllow();
		if (tempBlockAllow != null && !tempBlockAllow) {
			wallConfig.setBlockAllow(false);
		}
		Boolean tempConditionAndAlwayTrueAllow = c.getConfig().getConditionAndAlwayTrueAllow() == null ? g.getConfig().getConditionAndAlwayTrueAllow() : c.getConfig().getConditionAndAlwayTrueAllow();
		if (tempConditionAndAlwayTrueAllow != null && tempConditionAndAlwayTrueAllow) {
			wallConfig.setConditionAndAlwayTrueAllow(true);
		}
		Boolean tempConditionAndAlwayFalseAllow = c.getConfig().getConditionAndAlwayFalseAllow() == null ? g.getConfig().getConditionAndAlwayFalseAllow() : c.getConfig().getConditionAndAlwayFalseAllow();
		if (tempConditionAndAlwayFalseAllow != null && tempConditionAndAlwayFalseAllow) {
			wallConfig.setConditionAndAlwayFalseAllow(true);
		}
		Boolean tempConditionDoubleConstAllow = c.getConfig().getConditionDoubleConstAllow() == null ? g.getConfig().getConditionDoubleConstAllow() : c.getConfig().getConditionDoubleConstAllow();
		if (tempConditionDoubleConstAllow != null && tempConditionDoubleConstAllow) {
			wallConfig.setConditionDoubleConstAllow(true);
		}
		Boolean tempConditionLikeTrueAllow = c.getConfig().getConditionLikeTrueAllow() == null ? g.getConfig().getConditionLikeTrueAllow() : c.getConfig().getConditionLikeTrueAllow();
		if (tempConditionLikeTrueAllow != null && !tempConditionLikeTrueAllow) {
			wallConfig.setConditionLikeTrueAllow(false);
		}
		Boolean tempSelectAllColumnAllow = c.getConfig().getSelectAllColumnAllow() == null ? g.getConfig().getSelectAllColumnAllow() : c.getConfig().getSelectAllColumnAllow();
		if (tempSelectAllColumnAllow != null && !tempSelectAllColumnAllow) {
			wallConfig.setSelectAllColumnAllow(false);
		}
		Boolean tempDeleteAllow = c.getConfig().getDeleteAllow() == null ? g.getConfig().getDeleteAllow() : c.getConfig().getDeleteAllow();
		if (tempDeleteAllow != null && !tempDeleteAllow) {
			wallConfig.setDeleteAllow(false);
		}
		Boolean tempDeleteWhereAlwayTrueCheck = c.getConfig().getDeleteWhereAlwayTrueCheck() == null ? g.getConfig().getDeleteWhereAlwayTrueCheck() : c.getConfig().getDeleteWhereAlwayTrueCheck();
		if (tempDeleteWhereAlwayTrueCheck != null && !tempDeleteWhereAlwayTrueCheck) {
			wallConfig.setDeleteWhereAlwayTrueCheck(false);
		}
		Boolean tempDeleteWhereNoneCheck = c.getConfig().getDeleteWhereNoneCheck() == null ? g.getConfig().getDeleteWhereNoneCheck() : c.getConfig().getDeleteWhereNoneCheck();
		if (tempDeleteWhereNoneCheck != null && tempDeleteWhereNoneCheck) {
			wallConfig.setDeleteWhereNoneCheck(true);
		}
		Boolean tempUpdateAllow = c.getConfig().getUpdateAllow() == null ? g.getConfig().getUpdateAllow() : c.getConfig().getUpdateAllow();
		if (tempUpdateAllow != null && !tempUpdateAllow) {
			wallConfig.setUpdateAllow(false);
		}
		Boolean tempUpdateWhereAlayTrueCheck = c.getConfig().getUpdateWhereAlayTrueCheck() == null ? g.getConfig().getUpdateWhereAlayTrueCheck() : c.getConfig().getUpdateWhereAlayTrueCheck();
		if (tempUpdateWhereAlayTrueCheck != null && !tempUpdateWhereAlayTrueCheck) {
			wallConfig.setUpdateWhereAlayTrueCheck(false);
		}
		Boolean tempUpdateWhereNoneCheck = c.getConfig().getUpdateWhereNoneCheck() == null ? g.getConfig().getUpdateWhereNoneCheck() : c.getConfig().getUpdateWhereNoneCheck();
		if (tempUpdateWhereNoneCheck != null && tempUpdateWhereNoneCheck) {
			wallConfig.setUpdateWhereNoneCheck(true);
		}
		Boolean tempInsertAllow = c.getConfig().getInsertAllow() == null ? g.getConfig().getInsertAllow() : c.getConfig().getInsertAllow();
		if (tempInsertAllow != null && !tempInsertAllow) {
			wallConfig.setInsertAllow(false);
		}
		Boolean tempMergeAllow = c.getConfig().getMergeAllow() == null ? g.getConfig().getMergeAllow() : c.getConfig().getMergeAllow();
		if (tempMergeAllow != null && !tempMergeAllow) {
			wallConfig.setMergeAllow(false);
		}
		Boolean tempMinusAllow = c.getConfig().getMinusAllow() == null ? g.getConfig().getMinusAllow() : c.getConfig().getMinusAllow();
		if (tempMinusAllow != null && !tempMinusAllow) {
			wallConfig.setMinusAllow(false);
		}
		Boolean tempIntersectAllow = c.getConfig().getIntersectAllow() == null ? g.getConfig().getIntersectAllow() : c.getConfig().getIntersectAllow();
		if (tempIntersectAllow != null && !tempIntersectAllow) {
			wallConfig.setIntersectAllow(false);
		}
		Boolean tempReplaceAllow = c.getConfig().getReplaceAllow() == null ? g.getConfig().getReplaceAllow() : c.getConfig().getReplaceAllow();
		if (tempReplaceAllow != null && !tempReplaceAllow) {
			wallConfig.setReplaceAllow(false);
		}
		Boolean tempSetAllow = c.getConfig().getSetAllow() == null ? g.getConfig().getSetAllow() : c.getConfig().getSetAllow();
		if (tempSetAllow != null && !tempSetAllow) {
			wallConfig.setSetAllow(false);
		}
		Boolean tempCommitAllow = c.getConfig().getCommitAllow() == null ? g.getConfig().getCommitAllow() : c.getConfig().getCommitAllow();
		if (tempCommitAllow != null && !tempCommitAllow) {
			wallConfig.setCommitAllow(false);
		}
		Boolean tempRollbackAllow = c.getConfig().getRollbackAllow() == null ? g.getConfig().getRollbackAllow() : c.getConfig().getRollbackAllow();
		if (tempRollbackAllow != null && !tempRollbackAllow) {
			wallConfig.setRollbackAllow(false);
		}
		Boolean tempUseAllow = c.getConfig().getUseAllow() == null ? g.getConfig().getUseAllow() : c.getConfig().getUseAllow();
		if (tempUseAllow != null && !tempUseAllow) {
			wallConfig.setUseAllow(false);
		}
		Boolean tempMultiStatementAllow = c.getConfig().getMultiStatementAllow() == null ? g.getConfig().getMultiStatementAllow() : c.getConfig().getMultiStatementAllow();
		if (tempMultiStatementAllow != null && tempMultiStatementAllow) {
			wallConfig.setMultiStatementAllow(true);
		}
		Boolean tempTruncateAllow = c.getConfig().getTruncateAllow() == null ? g.getConfig().getTruncateAllow() : c.getConfig().getTruncateAllow();
		if (tempTruncateAllow != null && !tempTruncateAllow) {
			wallConfig.setTruncateAllow(false);
		}
		Boolean tempCommentAllow = c.getConfig().getCommentAllow() == null ? g.getConfig().getCommentAllow() : c.getConfig().getCommentAllow();
		if (tempCommentAllow != null && tempCommentAllow) {
			wallConfig.setCommentAllow(true);
		}
		Boolean tempStrictSyntaxCheck = c.getConfig().getStrictSyntaxCheck() == null ? g.getConfig().getStrictSyntaxCheck() : c.getConfig().getStrictSyntaxCheck();
		if (tempStrictSyntaxCheck != null && !tempStrictSyntaxCheck) {
			wallConfig.setStrictSyntaxCheck(false);
		}
		Boolean tempConstArithmeticAllow = c.getConfig().getConstArithmeticAllow() == null ? g.getConfig().getConstArithmeticAllow() : c.getConfig().getConstArithmeticAllow();
		if (tempConstArithmeticAllow != null && !tempConstArithmeticAllow) {
			wallConfig.setConstArithmeticAllow(false);
		}
		Boolean tempLimitZeroAllow = c.getConfig().getLimitZeroAllow() == null ? g.getConfig().getLimitZeroAllow() : c.getConfig().getLimitZeroAllow();
		if (tempLimitZeroAllow != null && tempLimitZeroAllow) {
			wallConfig.setLimitZeroAllow(true);
		}
		Boolean tempDescribeAllow = c.getConfig().getDescribeAllow() == null ? g.getConfig().getDescribeAllow() : c.getConfig().getDescribeAllow();
		if (tempDescribeAllow != null && !tempDescribeAllow) {
			wallConfig.setDescribeAllow(false);
		}
		Boolean tempShowAllow = c.getConfig().getShowAllow() == null ? g.getConfig().getShowAllow() : c.getConfig().getShowAllow();
		if (tempShowAllow != null && !tempShowAllow) {
			wallConfig.setShowAllow(false);
		}
		Boolean tempSchemaCheck = c.getConfig().getSchemaCheck() == null ? g.getConfig().getSchemaCheck() : c.getConfig().getSchemaCheck();
		if (tempSchemaCheck != null && !tempSchemaCheck) {
			wallConfig.setSchemaCheck(false);
		}
		Boolean tempTableCheck = c.getConfig().getTableCheck() == null ? g.getConfig().getTableCheck() : c.getConfig().getTableCheck();
		if (tempTableCheck != null && !tempTableCheck) {
			wallConfig.setTableCheck(false);
		}
		Boolean tempFunctionCheck = c.getConfig().getFunctionCheck() == null ? g.getConfig().getFunctionCheck() : c.getConfig().getFunctionCheck();
		if (tempFunctionCheck != null && !tempFunctionCheck) {
			wallConfig.setFunctionCheck(false);
		}
		Boolean tempObjectCheck = c.getConfig().getObjectCheck() == null ? g.getConfig().getObjectCheck() : c.getConfig().getObjectCheck();
		if (tempObjectCheck != null && !tempObjectCheck) {
			wallConfig.setObjectCheck(false);
		}
		Boolean tempVariantCheck = c.getConfig().getVariantCheck() == null ? g.getConfig().getVariantCheck() : c.getConfig().getVariantCheck();
		if (tempVariantCheck != null && !tempVariantCheck) {
			wallConfig.setVariantCheck(false);
		}
		Boolean tempMustParameterized = c.getConfig().getMustParameterized() == null ? g.getConfig().getMustParameterized() : c.getConfig().getMustParameterized();
		if (tempMustParameterized != null && tempMustParameterized) {
			wallConfig.setMustParameterized(true);
		}
		Boolean tempDoPrivilegedAllow = c.getConfig().getDoPrivilegedAllow() == null ? g.getConfig().getDoPrivilegedAllow() : c.getConfig().getDoPrivilegedAllow();
		if (tempDoPrivilegedAllow != null && tempDoPrivilegedAllow) {
			wallConfig.setDoPrivilegedAllow(true);
		}
		Boolean tempWrapAllow = c.getConfig().getWrapAllow() == null ? g.getConfig().getWrapAllow() : c.getConfig().getWrapAllow();
		if (tempWrapAllow != null && !tempWrapAllow) {
			wallConfig.setWrapAllow(false);
		}
		Boolean tempMetadataAllow = c.getConfig().getMetadataAllow() == null ? g.getConfig().getMetadataAllow() : c.getConfig().getMetadataAllow();
		if (tempMetadataAllow != null && !tempMetadataAllow) {
			wallConfig.setMetadataAllow(false);
		}
		Boolean tempConditionOpXorAllow = c.getConfig().getConditionOpXorAllow() == null ? g.getConfig().getConditionOpXorAllow() : c.getConfig().getConditionOpXorAllow();
		if (tempConditionOpXorAllow != null && tempConditionOpXorAllow) {
			wallConfig.setConditionOpXorAllow(true);
		}
		Boolean tempConditionOpBitwseAllow = c.getConfig().getConditionOpBitwseAllow() == null ? g.getConfig().getConditionOpBitwseAllow() : c.getConfig().getConditionOpBitwseAllow();
		if (tempConditionOpBitwseAllow != null && !tempConditionOpBitwseAllow) {
			wallConfig.setConditionOpBitwseAllow(false);
		}
		Boolean tempCaseConditionConstAllow = c.getConfig().getCaseConditionConstAllow() == null ? g.getConfig().getCaseConditionConstAllow() : c.getConfig().getCaseConditionConstAllow();
		if (tempCaseConditionConstAllow != null && tempCaseConditionConstAllow) {
			wallConfig.setCaseConditionConstAllow(true);
		}
		Boolean tempCompleteInsertValuesCheck = c.getConfig().getCompleteInsertValuesCheck() == null ? g.getConfig().getCompleteInsertValuesCheck() : c.getConfig().getCompleteInsertValuesCheck();
		if (tempCompleteInsertValuesCheck != null && tempCompleteInsertValuesCheck) {
			wallConfig.setCompleteInsertValuesCheck(true);
		}
		return wallConfig;
	}
}
