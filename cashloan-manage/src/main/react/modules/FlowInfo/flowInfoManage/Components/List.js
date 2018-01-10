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
    //点击量
    showModal1(title, record, canEdit) {
        // var record = this.state.selectedRow;
        this.setState({
            visibleLook: true,
            canEdit: canEdit,
            record: record,
            title: title
        },() => {
            Utils.ajaxData({
                url: '/act/flowControl/getPlatFormClick.htm',
                method: "post",
                data: {code:record.pcode},
                callback: (result) => {
                    if(result.code == '200'){
                        this.refs.Lookdetails.setFieldsValue(result);
                        this.setState({
                            detail: result.data,
                        });
                    }else{
                        Modal.error({
                            title: result.msg
                        })
                    }

                }
            });
        })
    },
    //新增跟修改弹窗
    showModal(title, record, canEdit) {
        var record = record;
        if (title == '修改') {
            var record = record;
            record.ptag = record.ptag.split(',');
            record.pprocess = record.pprocess.split(',');
            this.refs.AddFlowInfo.setFieldsValue(record);
            //console.log(record);
        } else if (title == '新增') {
            record = null
        }
        this.setState({
            canEdit: canEdit,
            visible: true,
            title: title,
            record: record
        });
    },
    //打开分配弹窗
    showAssignModal(title, record) {
        this.setState({
            assignVisible: true,
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
        /*console("翻页查询参数>>>>"+this.state.params);
        this.fetch({
            pageSize: pagination.pageSize,
            current: pagination.current,
            params:this.state.params
        });*/
    },
    fetch(params = {
        pageSize: 10,
        current: 1
    }) {
        this.setState({
            loading: true
        });
        Utils.ajaxData({
            url: '/act/flowControl/getInfoManage.htm',
            data: params,
            callback: (result) => {
                console.log(result)
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
    clearList() {
        this.setState({
            selectedRowKeys: [],
        });
    },
    refreshList() {
        this.fetch();
    },
    delete(record) {
        var me = this;
        confirm({
            title: '确认要删除这项内容,不可恢复！',
            onOk: function () {
                Utils.ajaxData({
                    url: "/act/flowControl/delete.htm",
                    data: {
                        id: record.id,
                        code: record.code,
                        picName:record.picName
                    },
                    method: 'post',
                    callback: (result) => {
                        Modal.success({
                            title: result.msg,
                        });
                        me.refreshList();
                    }
                });
            },
            onCancel: function () { }
        });
    },
    render() {
        var me = this;
        const {
            loading,
            selectedRowKeys
        } = this.state;
        const rowSelection = {
            selectedRowKeys,
            onChange: this.onSelectChange,
        };
        const hasSelected = selectedRowKeys.length > 0;
        var columns = [{
            title: '产品名称',
            dataIndex: 'pname'
        }, {
            title: '额度范围',
            dataIndex: "minLimit",
            render: function (value,record) {
                 const maxcount = record.maxLimit;
                 if(maxcount!=undefined && maxcount!="" && maxcount!=null ){
                    return value + "~"+maxcount;
                 }else {
                     return value;
                 }
            }
        }, {
            title: '放款时间',
            dataIndex: "ploanMinTime",
            render: function (value,record) {
                var tempTimes = "";
                const maxLoanTime = record.ploanMaxTime;
                if(maxLoanTime!=undefined && maxLoanTime!="" && maxLoanTime!=null && maxLoanTime!=0 ){
                    tempTimes = value + "~"+maxLoanTime;
                }else {
                    tempTimes =  value;
                }
                if(record.ploanTimeType == 1){
                    return tempTimes+"小时";
                }else if(record.ploanTimeType == 2){
                    return tempTimes+"天";
                }else if(record.ploanTimeType == 3){
                    return tempTimes+"分钟";
                }else{
                    return tempTimes;
                }
            }
        },  {
            title: '链接地址',
            dataIndex: "id"
        }, {
            title: '产品说明',
            dataIndex: "premark",
            render: (text, record) => {
                if (text&&text.length >= 8) {
                    return <Popover content={text} overlayStyle={{ width: "200px" }}>
                        <span>{text.substring(0, 8)}...</span>
                    </Popover>
                } else {
                    return <span>{text}</span>
                }
            }
        },{
            title: '产品编码',
            dataIndex: "pcode"
        },{
            title: '渠道价格',
            dataIndex: "pChannelPrice"
        },{
            title: '录入人信息',
            dataIndex: "pHandPerson"
        },
        {
            title: '排序',
            dataIndex: "psort"
        }, {
                title: '开启状态',
                dataIndex: "pstate"  ,
                render: function (value) {
                    if (value == 10)
                        return "开启";
                    else return "关闭";
                }
            }, {
                title: '申请人数',
                dataIndex: "pBorrowNum"
            },{
            title: '操作',
            dataIndex: "",
            render(text, record) {
                return <div style={{ textAlign: "left" }}>
                    <a href="#" onClick={me.showModal1.bind(me, '点击量', record, false)}>点击量</a>
                    <span className="ant-divider"></span>
                    <a href="#" onClick={me.showModal.bind(null, '修改', record, true) }>修改 </a>
                    <span className="ant-divider"></span>
                    <a href="#" onClick={me.delete.bind(null, record) }>删除</a>
                    <span className="ant-divider"></span>
                </div>
            }
        }];
        var state = this.state;
        return (
            <div className="block-panel">
                <div className="actionBtns" style={{ marginBottom: 16 }}>
                    <button className="ant-btn" onClick={this.showModal.bind(this, '新增', null, true) }>
                        新增
                    </button>
                </div>
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