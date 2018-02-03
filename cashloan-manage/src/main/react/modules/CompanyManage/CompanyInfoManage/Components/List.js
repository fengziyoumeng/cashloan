import React from 'react'
import {
    Table,
    Modal,
    Popover
} from 'antd';
import Lookdetails from './Lookdetails'
import AddFlowInfo from './AddFlowInfo'
import AssignPermissions from './AssignPermissions'
var confirm = Modal.confirm;
export default React.createClass({
    getInitialState() {
        return {
            selectedRowKeys: [], // 这里配置默认勾选列
            loading: false,
            data: [],
            pagination: {},
            canEdit: true,
            visible: false,
            visibleLook: false,
            assignVisible: false,
        };
    },
    hideModal() {
        this.setState({
            visible: false,
            visibleLook: false,
            assignVisible: false
        });
        var pagination = this.state.pagination;
        this.fetch();
    },
    //立即申请
    showModal(title, record, canEdit) {
        var record = record;
        this.refs.AddFlowInfo.setFieldsValue(record);
        this.setState({
            canEdit: canEdit,
            visible: true,
            title: title,
            record: record
        });
    },
    //审核详情
    showModalDetail(title, record, canEdit) {
        var record = record;
        this.refs.AddFlowInfo.setFieldsValue(record);
        this.setState({
            canEdit: canEdit,
            visible: true,
            title: title,
            record: record
        });
    },
    rowKey(record) {
        return record.id;
    },
    componentWillReceiveProps(nextProps, nextState) {
        this.setState({
            params: nextProps.params,
        });
        this.fetch(nextProps.params);
    },
    componentDidMount() {
        this.fetch();

    },
    //分页
    handleTableChange(pagination, filters, sorter) {
        const pager = this.state.pagination;
        pager.current = pagination.current;
        this.setState({
            pagination: pager,
        });
    },
    //获取数据
    fetch(params = {
        pageSize: 10,
        current: 1
    }) {
        this.setState({
            loading: true
        });
        Utils.ajaxData({
            url: '/act/model/company/auditList.htm',
            data: params,
            callback: (result) => {
                // console.info("=======>"+JSON.stringify(result.data));
                const pagination = this.state.pagination;
                pagination.total = result.totalCount;
                if (!pagination.current) {
                    pagination.current = 1
                };
                this.setState({
                    loading: false,
                    data: result.data,
                    pagination,
                });
                this.clearList();
            }
        });
    },
    //清空列表
    clearList() {
        this.setState({
            selectedRowKeys: [],
        });
    },
    //刷新列表
    refreshList() {
        this.fetch();
    },
    render() {
        var me = this;
        var columns = [{
            title: '企业名称',
            dataIndex: 'companyName'
        },{
            title: '法人名称',
            dataIndex: "legalPersonName",
        },{
            title: '身份证号码',
            dataIndex: "idnumber",
            render: function (value,record) {
                var str1 = value.substring(0,5);
                var str2 = value.substring(13,18);
                var str = str1+"******"+str2;
                return str;
            }
        },{
            title: '企业联系人',
            dataIndex: "contactPerson"
        },{
            title: '联系人手机号',
            dataIndex: "contactTel"
        },{
            title: '审核状态',
            dataIndex: "auditState",
            render:function(value,record){
                if(value === 1){
                    return <font color='blue'>待审核</font>;
                }else if(value === 2){
                    return <font color='green'>审核通过</font>;
                }else if(value === 3){
                    return <font color='red'>审核拒绝</font>;
                }
            }
        },{
            title: '审核人',
            dataIndex: "auditPerson",
            render:function(value,record){
                if(value){
                    return value;
                }else{
                    return "---";
                }
            }
        },{
            title: '启用状态',
            dataIndex: "state",
            render:function(value,record){
                if(value === 10){
                    return "开启";
                }else if(value === 20){
                    return "关闭";
                }
            }
        },{
            title: '提交时间',
            dataIndex: "updateTime"
        },{
            title: '操作',
            dataIndex: "",
            render:function(value, record) {
                if(record.auditState==1){
                    return <div style={{ textAlign: "left" }}>
                        <a href="#" onClick={me.showModal.bind(null, '资质审核', record, true)}>立即审核</a>
                    </div>
                }else{
                    return <div style={{ textAlign: "left" }}>
                        <a href="#" onClick={me.showModalDetail.bind(null, '审核详情', record, false)}>查看详情</a>
                    </div>
                }
        }}];

        var state = this.state;
        return (
            <div className="block-panel">
                <Table columns={columns} rowKey={this.rowKey}  size="middle"  params ={this.props.params}
                       dataSource={this.state.data}
                       pagination={this.state.pagination}
                       loading={this.state.loading}
                       onChange={this.handleTableChange}  />
                <AddFlowInfo ref="AddFlowInfo"  visible={state.visible}    title={state.title} hideModal={me.hideModal} record={state.record}
                             canEdit={state.canEdit}/>
                <Lookdetails ref="Lookdetails" visible={state.visibleLook} title={state.title} hideModal={me.hideModal} record={state.rowRecord}
                             canEdit={state.canEdit} detail={state.detail} />
                <AssignPermissions ref="AssignPermissions"  visible={state.assignVisible}    title={state.title} hideModal={me.hideModal} selectRecord={state.record}
                                   canEdit={state.canEdit}/>
            </div>
        );
    }
})