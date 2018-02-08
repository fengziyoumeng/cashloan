import React from 'react';
import './style.css';
import Bar1 from './Bar1';
import Bar2 from './Bar2';

var Reflux = require('reflux');
var AppActions = require('../../frame/actions/AppActions');
var UserMessageStore = require('../../frame/stores/UserMessageStore');
export default React.createClass({
    mixins: [
        Reflux.connect(UserMessageStore, "userMessage")
    ],
    getInitialState() {
        return {
            menuData: [],
            assetsData: {},
            investmentData: {},
            loading: false,
            data: [],
            userMessage: {},
        }
    },
    fetch() {
        var me = this;
        this.setState({
            loading: true
        });
        Utils.ajaxData({
            url: '/modules/manage/count/homeInfo.htm',
            method: "get",
            callback: (result) => {
                console.log(result.data);
                me.setState({
                    loading: false,
                    data: result.data,
                });
            }
        });
    },

    componentDidMount() {
        AppActions.initUserMessageStore();
        this.fetch();
    },
    render() {
        var { data } = this.state;
        var userMessage = this.state.userMessage;
        return (
            <div style={{ minWidth: 1350,display: userMessage.name&&userMessage.name!='代理商' ? 'block' : 'none' }}>
                <div className="block-panel">
                    <h2 className="navLine-title">注册数据</h2>
                    <div className='blk-top'>
                        <div className='blk-top-item'>
                            <div className='blk-title'>当天注册数</div>
                            <div className='blk-number'>{data.todayRegister}</div>
                        </div>
                        <div className='blk-top-item'>
                            <div className='blk-title'>当月注册数</div>
                            <div className='blk-number'>{data.monthRegister}</div>
                        </div>
                        <div className='blk-top-item'>
                            <div className='blk-title'>总注册数</div>
                            <div className='blk-number'>{data.totalRegister}</div>
                        </div>
                    </div>
                </div>

                <div className="block-chart">
                    <div className='blk-top'>
                        <div className='blk-top-item blk-top-item-last'>
                            <Bar1 data={data} />
                            <Bar2 data={data} />
                        </div>
                    </div>
                </div>
            </div>
        )
    }
});