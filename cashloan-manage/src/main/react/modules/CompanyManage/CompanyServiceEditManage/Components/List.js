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
            url: '/act/model/companyservice/alllist.htm',
            data: params,
            callback: (result) => {
                // console.info("=======>"+JSON.stringify(result));
                const pagination = this.state.pagination;
                /*pagination.total = result.totalCount;*/
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
            title: '公司名称',
            dataIndex: "companyInfo",
            render: function (value,record) {
                if(value){
                    return value.companyName;
                }
            }
        },{
            title: 'LOGO',
            dataIndex: "logo_path",
            render: function (value,record) {
                return  <img src={value} alt=""  style={{width: '50px',marginLeft:'5px'}}/>;
            }
        },{
            title: '服务名称',
            dataIndex: 'proc_name'
        },{
            title: '类别',
            dataIndex: "companyProd",
            render: function (value,record) {
                return value.type_name;
            }
        },{
            title: '审核状态',
            dataIndex: "audit_state",
            render:function(value,record){
                if(value === 1){
                    return "待审核";
                }else if(value === 2){
                    return "审核通过";
                }else if(value === 3){
                    return "审核拒绝";
                }
            }
        },{
            title: '启用状态',
            dataIndex: "status",
            render:function(value,record){
                if(value === 1){
                    return "开启";
                }else if(value === 0){
                    return "关闭";
                }
            }
        },{
            title: '提交时间',
            dataIndex: "update_time"
        },{
            title: '操作',
            dataIndex: "",
            render(text, record) {
                return <div style={{ textAlign: "left" }}>
                    <a href="#" onClick={me.showModal.bind(null, '修改信息', record, true)}>修改信息</a>
                </div>
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