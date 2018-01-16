var RepaymentPlanList = require('../../RepaymentManage/RepaymentPlanList/index');//还款管理-还款计划
var PaymentHistory = require('../../RepaymentManage/PaymentHistory/index');//还款管理-还款记录
var AlipayPaymentList = require('../../RepaymentManage/AlipayPaymentList/index');//还款管理-支付宝还款列表
var BankCardPaymentList = require('../../RepaymentManage/BankCardPaymentList/index');//还款管理-银行卡还款列表
var DeductionsList = require('../../RepaymentManage/DeductionsList/index');//扣款列表
var StayDeductionsList = require('../../RepaymentManage/StayDeductionsList/index');//待扣款列表
var UserBasicInformation = require('../../UserInformation/UserBasicInformation/index');//客户管理-用户基本信息——用户信息
var UserAuthenticationList = require('../../UserInformation/UserAuthenticationList/index');//客户管理-用户认证信息
var UserFeedback = require('../../UserInformation/UserFeedback/index');//客户管理-用户反馈
var UserEducationList = require('../../UserInformation/UserEducationList/index');//客户管理-用户教育信息列表
var BlackCustomerList = require('../../UserInformation/BlackCustomerList/index');//客户管理-黑名单
var ScoreRank = require('../../../creditrank/ScoreRank/index');//信用等级管理-评分等级
var AdjustCreditLine = require('../../../creditrank/AdjustCreditLine/index');//调整授信额度
var TableFieldMaintenance = require('../../../creditrank/TableFieldMaintenance/index');//信用评级表单字段维护
var AssessScoreCard = require('../../../creditrank/AssessScoreCard/index');//信用等级管理-评分卡
var LineTypeManage = require('../../../creditrank/LineTypeManage/index');//信用等级管理-额度类型管理
var BorrowingRulesManagement = require('../../BorrowingRulesManagement/index');//规则类型绑定
var LoanList = require('../../UserLoanManage/LoanList/index');//贷前管理-放款列表（放款订单-wangxb）
var OverdueList = require('../../UserLoanManage/OverdueList/index');//贷前管理-逾期列表
var RepaymentList = require('../../UserLoanManage/RepaymentList/index');//还款列表
var BadDebtsList = require('../../UserLoanManage/BadDebtsList/index');//已坏账列表
var LoanSchedule = require('../../LoanSchedule/index');//贷中管理-借款进度
var ruleEngine = require('../../ruleEngine/index');//规则配置
var sysUserManage = require('../../SystemMng/UserMang/index');//用户管理
var sysRoleManage = require('../../SystemMng/RoleMang/index');//角色管理
var AccessCode = require('../../SystemMng/AccessCode/index');//访问码管理
var Druid = require('../../SystemMng/Druid/index');//Druid
var sysMenuManage = require('../../SystemMng/MenuMang/index');//菜单管理
var sysDicManage = require('../../SystemMng/DictionaryMang/index');//字典管理
var SystemParameterSettings = require('../../SystemMng/SystemParameterSettings/index');//系统参数设置


var flowInfoManage = require('../../FlowInfo/flowInfoManage/index');//流量平台管理
var flowInfoUVPlatForm = require('../../FlowInfo/flowInfoUVPlatForm/index');//流量平台统计--UV统计
var ChannelH5Manage = require('../../FlowInfo/ChannelH5Manage/index');//推广注册统计
var ChannelH5ManageTwo = require('../../FlowInfo/ChannelH5ManageTwo/index');//推广注册统计-按80%来呈现
var BannerManage = require('../../PlatFormManage/BannerManage/index');//Banner管理
var NewBannerManage = require('../../PlatFormManage/NewBannerManage/index');//新Banner管理，用于金融圈子
var ClickTrackManage = require('../../FlowInfo/ClickTrackManage/index');//点击轨迹统计
var CompanyInfoManage = require('../../CompanyManage/CompanyInfoManage/index'); //公司入驻审核
var CategoryImageManage = require('../../PlatFormManage/CategoryImageManage/index'); //分类图标管理
var AdInfoManage = require('../../PlatFormManage/AdInfoManage/index'); //广告信息管理
var CompanyServiceManage = require('../../CompanyManage/CompanyServiceManage/index'); //公司服务审核
var CompanyServiceEditManage = require('../../CompanyManage/CompanyServiceEditManage/index'); //公司服务修改





module.exports = {
  RepaymentPlanList,
  PaymentHistory,
  AlipayPaymentList,
  BankCardPaymentList,
  DeductionsList,
  StayDeductionsList,
  UserBasicInformation,
  UserAuthenticationList,
  UserFeedback,
  UserEducationList,
  BlackCustomerList,
  ScoreRank,
  AdjustCreditLine,
  TableFieldMaintenance,
  AssessScoreCard,
  LineTypeManage,
  BorrowingRulesManagement,
  LoanList,
  OverdueList,
  RepaymentList,
  BadDebtsList,
  ruleEngine,
  sysUserManage,
  sysRoleManage,
  AccessCode,
  Druid,
  sysMenuManage,
  sysDicManage,
  SystemParameterSettings,
  LoanSchedule,
  flowInfoManage,
  ChannelH5Manage,
  ChannelH5ManageTwo,
  BannerManage,
  NewBannerManage,
  flowInfoUVPlatForm,
  ClickTrackManage,
  CompanyInfoManage,
  CategoryImageManage,
  AdInfoManage,
  CompanyServiceManage,
  CompanyServiceEditManage

}