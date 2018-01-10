import React from 'react'
import { Table, Modal, Icon } from 'antd';
import Lookdetails from "./Lookdetails"
const objectAssign = require('object-assign');
export default React.createClass({
    getInitialState() {
        return {
            selectedRowKeys: [], // 这里配置默认勾选列
            loading: false,
            data: [],
            pagination: {
                pageSize: 10,
                current: 1
            },
            canEdit: true,
            visible: false,
            visible1: false,
            visible2: false,
            pictureData: [],
            creditReportData: [],
            rowRecord: [],
            dataRecord: '',
            recordSoure: '',
            dataForm: '',
            canCheck: 'white'
        };
    },

    componentWillReceiveProps(nextProps, nextState) {
        this.fetch(nextProps.params);
    },

    componentDidMount() {
        this.fetch(this.props.params);
    },

    fetch(params = {}) {
        this.setState({
            loading: true
        });
        if (!params.pageSize) {
            var params = {};
            params = {
                searchParams: JSON.stringify({ state: "" }),
                pageSize: 10,
                current: 1,
            }
        }
        if(!params.searchParams){
            params.searchParams= JSON.stringify({state:''})
        }
        Utils.ajaxData({
            url: '/modules/manage/borrow/reviewList.htm',
            method: "post",
            data: params,
            callback: (result) => {
                const pagination = this.state.pagination;
                pagination.current = params.current;
                pagination.pageSize = params.pageSize;
                pagination.total = result.page.total;
                pagination.showTotal = () => `总共 ${result.page.total} 条`;
                this.setState({
                    loading: false,
                    data: result.data,
                    pagination
                });
            }
        });
    },

    //查看弹窗
    showModal(title, record, canEdit) {
        console.log(record)
        if(record.state==26){
            record.state="人工复审通过"
        }else{
            if(record.rejectReason=="01"){
                record.state= "人工复审拒绝/停机、关机、空号";
            } if(record.rejectReason=="02"){
                record.state= "人工复审拒绝/二次拨打未联系上";
            }if(record.rejectReason=="03"){
                record.state= "人工复审拒绝/放弃本次申请";
            }if(record.rejectReason=="04"){
                record.state= "人工复审拒绝/活体遮挡";
            }if(record.rejectReason=="05"){
                record.state= "人工复审拒绝/身份证非实拍、假证";
            }if(record.rejectReason=="06"){
                record.state="人工复审拒绝/紧急联系人虚假";
            }if(record.rejectReason=="07"){
                record.state= "人工复审拒绝/联系人少于20个";
            }if(record.rejectReason=="08"){
                record.state= "人工复审拒绝/专线电话超20个";
            }if(record.rejectReason=="09"){
                record.state= "人工复审拒绝/通话录丢失数据";
            }if(record.rejectReason=="10"){
                record.state= "人工复审拒绝/通话记录中包含催收电话";
            }if(record.rejectReason=="11"){
                record.state= "人工复审拒绝/户籍地不准入及偏远地区";
            }if(record.rejectReason=="12"){
                record.state="人工复审拒绝/职业不准入";
            }if(record.rejectReason=="13"){
                record.state= "人工复审拒绝/年龄不符";
            }if(record.rejectReason=="14"){
                record.state= "人工复审拒绝/本人不可信";
            }else{
                //历史数据没有reject_reason显示默认提示
                record.state= "人工复审拒绝";
            }
        }
        // var record = this.state.selectedRow;
        var record = record;
        var title = title;
        var me = this;
        this.setState({
            visible: true,
            canEdit: canEdit,
            rowRecord: record,
            title: title,
            keyModal: Date.now(),
        }, () => {
            Utils.ajaxData({
                url: '/modules/manage/borrow/qianchengReqLog/listByBorrow.htm',
                method: "post",
                data: {
                    borrowId: record.id,
                },
                callback: (result) => {
                    if (result.data) {
                        me.setState({
                            dataRecord: result.data
                        })
                    }
                    
                }
            });
            if(title != '查看'){
                Utils.ajaxData({
                    url: '/modules/manage/borrow/canAudit.htm',
                    method: "post",
                    data: {
                        borrowId: record.id,
                    },
                    callback: (result) => {
                        me.setState({
                            canCheck: result.data
                        })
                    }
                });
            }
            else{
                me.setState({
                    canCheck: '1'
                })
            }
            Utils.ajaxData({
                url: '/modules/manage/cl/cluser/detail.htm',
                data: {
                    userId: record.userId
                },
                callback: (result) => {
                    if (result.code == 200) {
                        var dataForm = {};
                        dataForm.realName = result.data.userbase.realName;
                        dataForm.sex = result.data.userbase.sex;
                        dataForm.idNo = result.data.userbase.idNo;                   
                        dataForm.age = result.data.userbase.age;
                        dataForm.cardNo = result.data.userbase.cardNo;
                        dataForm.bank = result.data.userbase.bank;
                        dataForm.bankPhone = result.data.userbase.bankPhone;
                        dataForm.phone = result.data.userbase.phone;
                        dataForm.channelAttr = result.data.operatorTdBasic.channelAttr;
                        dataForm.liveAddr = result.data.userbase.liveAddr;
                        dataForm.registTime = result.data.userbase.registTime;
                        dataForm.registerAddr = result.data.userbase.registerAddr;
                        dataForm.registerClient = result.data.userbase.registerClient;
                        dataForm.education = result.data.userbase.education;
                        dataForm.companyName = result.data.userbase.companyName;
                        dataForm.companyPhone = result.data.userbase.companyPhone;
                        dataForm.salary = result.data.userbase.salary;
                        dataForm.workingYears = result.data.userbase.workingYears;
                        dataForm.companyAddr = result.data.userbase.companyAddr;
                        dataForm.taobao = result.data.userOtherInfo != null ? result.data.userOtherInfo.taobao : '';
                        dataForm.email = result.data.userOtherInfo != null ? result.data.userOtherInfo.email : '';
                        dataForm.qq = result.data.userOtherInfo != null ? result.data.userOtherInfo.qq : '';
                        dataForm.wechat = result.data.userOtherInfo != null ? result.data.userOtherInfo.wechat : '';
                        dataForm.registerCoordinate = result.data.userbase.registerCoordinate;
                        if (result.data.userContactInfo.length > 0) {
                            if (result.data.userContactInfo[0].type == "10") {
                                dataForm.urgentName = result.data.userContactInfo[0].name;
                                dataForm.urgentPhone = result.data.userContactInfo[0].phone;
                                dataForm.urgentRelation = result.data.userContactInfo[0].relation;
                            } else {
                                dataForm.otherName = result.data.userContactInfo[0].name;
                                dataForm.otherPhone = result.data.userContactInfo[0].phone;
                                dataForm.otherRelation = result.data.userContactInfo[0].relation;
                            }
                            if (result.data.userContactInfo[1].type == "20") {
                                dataForm.otherName = result.data.userContactInfo[1].name;
                                dataForm.otherPhone = result.data.userContactInfo[1].phone;
                                dataForm.otherRelation = result.data.userContactInfo[1].relation;
                            } else {
                                dataForm.urgentName = result.data.userContactInfo[1].name;
                                dataForm.urgentPhone = result.data.userContactInfo[1].phone;
                                dataForm.urgentRelation = result.data.userContactInfo[1].relation;
                            }
                        }
                        if (result.data.userAuth) {
                            if (result.data.userAuth.bankCardState == 10) {
                                dataForm.bankCardState = "未认证"
                            } else if (result.data.userAuth.bankCardState == 20) {
                                dataForm.bankCardState = "认证中"
                            } else if (result.data.userAuth.bankCardState == 30) {
                                dataForm.bankCardState = "已认证"
                            }
                            if (result.data.userAuth.idState == 10) {
                                dataForm.idState = "未认证"
                            } else if (result.data.userAuth.idState == 20) {
                                dataForm.idState = "认证中"
                            } else if (result.data.userAuth.idState == 30) {
                                dataForm.idState = "已认证"
                            }
                            if (result.data.userAuth.phoneState == 10) {
                                dataForm.phoneState = "未认证"
                            } else if (result.data.userAuth.phoneState == 20) {
                                dataForm.phoneState = "认证中"
                            } else if (result.data.userAuth.phoneState == 30) {
                                dataForm.phoneState = "已认证"
                            }
                            if (result.data.userAuth.zhimaState == 10) {
                                dataForm.zhimaState = "未认证"
                            } else if (result.data.userAuth.zhimaState == 20) {
                                dataForm.zhimaState = "认证中"
                            } else if (result.data.userAuth.zhimaState == 30) {
                                dataForm.zhimaState = "已认证"
                            }
                        }
                        me.setState({
                            recordSoure: result.data,
                            dataForm:dataForm
                        })
                    }
                }
            });
            //console.log(record);
            record.state1 = title != "查看" ? "27" : record.state;
            this.refs.Lookdetails.setFieldsValue(record);
        })
    },

    //隐藏弹窗
    hideModal(state) {
        this.setState({
            visible: false,
            selectedIndex: '',
            selectedRowKeys: [],
            dataRecord: '',
            recordSoure: '',
            dataForm: '',
            canCheck: 'white'

        });
        this.refreshList();
    },


    rowKey(record) {
        return record.id;
    },

    //选择
    onSelectChange(selectedRowKeys) {
        this.setState({
            selectedRowKeys
        });
    },

    //分页
    handleTableChange(pagination, filters, sorter) {
        const pager = this.state.pagination;
        pager.current = pagination.current;
        this.setState({
            pagination: pager,
        });
        this.refreshList();
    },

    refreshList() {
        var pagination = this.state.pagination; //console.log(pagination)
        var params = objectAssign({}, this.props.params, {
            current: pagination.current,
            pageSize: pagination.pageSize,
            // searchParams: JSON.stringify({ state: "22" }),
        });
        this.fetch(params);
    },


    render() {
        const {
            loading,
            selectedRowKeys
            } = this.state;

        let me = this;
        const hasSelected = selectedRowKeys.length > 0;
        let openEdit = true;
        if (hasSelected && selectedRowKeys.indexOf("0") === -1) {
            openEdit = false;
        }
        var columns = [{
            title: '订单号',
            dataIndex: 'orderNo'
        }, {
            title: '真实姓名',
            dataIndex: 'realName'
        }, {
            title: '手机号码',
            dataIndex: "phone"
        }, {
            title: '借款金额(元)',
            dataIndex: "amount"
        }, {
            title: '借款期限(天)',
            dataIndex: "timeLimit"
        }, {
            title: '利息(元)',
            dataIndex: "interest"
        }, {
            title: '实际到账金额(元)',
            dataIndex: "realAmount"
        }, {
            title: '订单生产时间',
            dataIndex: 'createTime'
        }, {
            title: '费用(元)',
            dataIndex: 'fee'
        }, {
            title: '信息认证费(元)',
            dataIndex: "infoAuthFee",
        }, {
            title: '服务费(元)',
            dataIndex: "serviceFee",
        }, {
            title: '审核人',
            dataIndex: "auditor",
        }, {
            title: '状态',
            dataIndex: "stateStr",
        }, {
            title: '操作',
            render(text, record) {
                console.log(record);
                if (record.state == '22') {
                    return (
                        <div style={{ textAlign: "left" }}>
                            {record.auditProcess == '1' ? <a href="#" style={{color: 'red'}} onClick={me.showModal.bind(me, '人工复审中', record, true)}>人工复审进行中</a>  : <a href="#" onClick={me.showModal.bind(me, '人工复审', record, true)}>人工复审</a>}
                        </div>
                    )                    
                } else {
                    return (
                        <div style={{ textAlign: "left" }}>
                            <a href="#" onClick={me.showModal.bind(me, '查看', record, false)}>查看</a>
                        </div>
                    )
                }
            }
        }];

        var state = this.state;
        return (
            <div className="block-panel">

                <Table columns={columns} rowKey={this.rowKey} ref="table"
                    dataSource={this.state.data}
                    pagination={this.state.pagination}
                    onChange={this.handleTableChange}
                />
                <Lookdetails ref="Lookdetails" canCheck={state.canCheck} key={state.keyModal}  dataForm={state.dataForm} recordSoure={state.recordSoure} dataRecord={state.dataRecord} visible={state.visible} title={state.title} hideModal={me.hideModal} record={state.rowRecord}
                    canEdit={state.canEdit} />
            </div>
        );
    }
})
