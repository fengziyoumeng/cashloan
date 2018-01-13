/*角色管理*/
import React from 'react';
import List from './Components/List';
import SeachForm from './Components/SeachForm';
export default React.createClass({

    getInitialState() {
        return {
            params: {}
        }
    },
    passParams(params) {
        this.setState({
            params: params
        });
    },
    render() {
        return <div>
                    <div>
                        <SeachForm passParams={this.passParams}/>
                    </div>
                    <List params={this.state.params}/>
               </div>
    }
});
