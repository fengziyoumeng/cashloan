import React from 'react'
import {Table, Modal, Icon} from 'antd';
import Lookdetails from "./Lookdetails";
var repaymentTypeText={'10':'待审核', '20': '审核中' ,'30': '通过','40' :'已拒绝' ,'50': '还款中', '60' :'还款完成', '70': '逾期'}
const objectAssign = require('object-assign');
import AddWin from "./AddWin";
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
            rowRecord:[],
            record:"",
            visibleAdd:false,

        };
    },
    componentWillReceiveProps(nextProps, nextState) {
        this.fetch(nextProps.params);
    },

    componentDidMount() {
        this.fetch();
        []
    },

    fetch(params = {}) {
        // this.setState({
        //     loading: true
        // });
        // if (!params.pageSize) {
        //     var params = {};
        //     params = {
        //         pageSize: 10,
        //         current: 1
        //     }
        // }
        // Utils.ajaxData({
        //     url: '/modules/manage/borrow/list.htm',
        //     method: "post",
        //     data: params,
        //     callback: (result) => {
        //         const pagination = this.state.pagination;
        //         pagination.current = params.current;
        //         pagination.pageSize = params.pageSize;
        //         pagination.total = result.page.total;
        //         if (!pagination.current) {
        //             pagination.current = 1
        //         }
        //         ;
        //         this.setState({
        //             loading: false,
        //             data: result.data,
        //             pagination
        //         });
        //     }
        // });
        let arr = [['x5-1zdxT','H5-xyqd','WAP-guangdt37','WAP-lemo4'],[200,125,421,123],[120,55,230,22],[110,60,300,40],[20,10,50,5],[50,40,100,30],[30,20,200,10],[30,20,100,10],[10000,5000,20000,4000]]
        for(let i = 1; i < 10; i++){
            let n = Math.floor(Math.random()*4);
            let p = (arr[7][n]/arr[5][n]).toFixed(2);
            this.state.data.push({channelName: arr[0][n], regeditNumber: arr[1][n], realNameNumber: arr[2][n], cardNumber: arr[3][n], borrowNumber: arr[4][n], borrowTimes: arr[5][n], loanNumber: arr[6][n], loanTimes: arr[7][n], pass: p, loanAmount: arr[8][n]});
        }
        //console.log(this.state.data);
        this.setState({
            // loading: false,
            data: this.state.data
        })
    },

    //查看弹窗
    showModal(title,record, canEdit) {
      
        this.setState({
            visible: true,
            canEdit: canEdit,
            record: record,
            title: title
        },()=>{ 
            
            this.refs.Lookdetails.setFieldsValue(record);
        })
    },
  //新增
  addModal(title, record, canEdit){
      this.setState({
        visibleAdd:true,
        title:title,  
      })

  },
    //隐藏弹窗
    hideModal() {
        this.setState({
            visible: false,
            visible1: false,
            visible2: false,
            selectedIndex: '',
            selectedRowKeys: [],
            visibleAdd:false
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
        var pagination = this.state.pagination;
        var params = objectAssign({}, this.props.params, {
            current: pagination.current,
            pageSize: pagination.pageSize
        });
        this.fetch(params);
    },

    //行点击事件
    onRowClick(record) {
        //console.log(record)
        this.setState({
            selectedRowKeys: [record.id],
            selectedRow: record,
            rowRecord:record
        },()=>{
            this
        });
    },

    // 列表添加选中行样式
    rowClassName(record) {
        let selected = this.state.selectedIndex;
        //console.log('selected', this.state.selectedIndex)
        return (record.id == selected && selected !== '') ? 'selectRow' : '';

    },

    //选择
    onSelectAll(selected, selectedRowKeys, selectedRows, changeRows) {
        if (selected) {
            this.setState({
                selectedRowKeys
            })
        } else {
            this.setState({
                selectedRowKeys: []
            })
        }
    },

    render() {
        const {
            loading,
            selectedRowKeys
            } = this.state;
        const rowSelection = {
            type: 'checkbox',
            selectedRowKeys,
            onSelectAll: this.onSelectAll,
        };
        let me=this;
        const hasSelected = selectedRowKeys.length > 0;
        let openEdit = true;
        if (hasSelected && selectedRowKeys.indexOf("0") === -1) {
            openEdit = false;
        }
        var columns = [{
            title: '渠道名称',
            dataIndex: 'channelName'
        }, {
            title: '注册数',
            dataIndex: 'regeditNumber'
        }, {
            title: '实名数',
            dataIndex: 'realNameNumber'
        }, {
            title: '绑卡数',
            dataIndex: 'cardNumber'
        }, {
            title: '借款人数',
            dataIndex: "borrowNumber",
        }, {
            title: '借款笔数',
            dataIndex: 'borrowTimes'
        },{
            title: '放款人数',
            dataIndex: "loanNumber"
        }, {
            title: '放款笔数',
            dataIndex: 'loanTimes'
        }, {
            title: '通过率(放款笔数/借款笔数)',
            dataIndex: "pass",
        }, {
            title: '放款金额',
            dataIndex: 'loanAmount'
        }]
        // },{
        //     title: '操作',
        //     dataIndex: "",
        //     render: (value,record) => {
        //         return(
        //            <div style={{ textAlign: "left" }}>
        //                 <a href="#" onClick={me.showModal.bind(me, '查看详情',record, false)}>查看详情</a>
        //            </div>  
        //         )
        //     } }]; 

        var state = this.state;
        return (
            <div className="block-panel">
                <div className="actionBtns" style={{ marginBottom: 16 }}>
                    {/*<button onClick={me.addModal.bind(me,'新增')} className="ant-btn"> 
                        新增(测试)
                    </button>*/}
                </div>
                <Table columns={columns} rowKey={this.rowKey} ref="table" 
                       onRowClick={this.onRowClick}
                       dataSource={this.state.data}
                       rowClassName={this.rowClassName}
                       pagination={this.state.pagination}
                       onChange={this.handleTableChange}
                />
                <Lookdetails ref="Lookdetails" visible={state.visible} title={state.title} hideModal={me.hideModal} record={state.record}
                canEdit={state.canEdit} />
                <AddWin ref="AddWin"  visible={state.visibleAdd} hideModal={me.hideModal} title={state.title}/>
            </div>
        );
    }
})
