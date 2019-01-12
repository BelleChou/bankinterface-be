<template>
  <div class="manage tc">
    <button v-on:click="add">新增</button>
    <el-dialog :visible.sync="showAdd">

      <form class="add-input">
        <h1>新增记录
        </h1>
        <el-row :gutter="20">

          <el-col :span="12">
            <div>
              <span>交易码 </span>
              <input v-model="interfaceNo" type="text" name="name"/>
            </div>
          </el-col>

          <el-col :span="12">
            <div>
              <span>交易码名称 </span>
              <input v-model="remarks" type="text" name="email"/>
            </div>
          </el-col>
          <!--<el-col :span="8"><div>
                      <span>作者 </span>
                <input v-model="writer"  type="text" name="email" />
                  </div></el-col>-->

        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <div><span>输入报文 </span>
              <textarea v-model="send" style="height: 500px;"></textarea></div>
          </el-col>
          <el-col :span="12">
            <div><span>输出报文 </span>
              <textarea v-model="receive" style="height: 500px;"></textarea></div>
          </el-col>
        </el-row>

      </form>


      <button v-on:click="bank_mapping_add()">确定</button>

    </el-dialog>

    <span style="margin-left: 150px;width:160px;height:33px; font-size: 33px;">交易码</span>
    <input type="text" v-model="interfaceNo" placeholder="请输入交易码" style="margin-left: 0px;width:160px;height:33px; "/>
    <span style="margin-left: 2px;width:160px;height:33px; font-size: 33px;">创建人</span>
    <input type="text" v-model="writer" placeholder="请输入创建人" style="margin-left: 0px;width:160px;height:33px; "/>
    <button v-on:click="bank_mapping_query">查询</button>
    <!--
                作者：isst
                时间：2018-08-24
                描述：主数据呈现
            -->

    <div class="div-tab">
      <el-table :data="bankLists" style="width: 100%" header-row-class-name="center"
                :default-sort="{prop: 'createDate', order: 'descending'}">
        <el-table-column prop="index" type="index" :index="indexMethod" width="50" label="序号" fixed>
        </el-table-column>
        <el-table-column prop="id" label="编号" width="50" fixed>
        </el-table-column>
        <el-table-column prop="interfaceNo" label="交易号" width="100" fixed>
        </el-table-column>
        <el-table-column prop="remarks" label="交易号名称" width="150" fixed>
        </el-table-column>
        <el-table-column prop="writer" label="作者" width="150" fixed>
        </el-table-column>
        <el-table-column prop="createDate" label="创建时间" width="100">
          <template slot-scope="scope">
            {{scope.row.createDate|timeForm}}
          </template>
        </el-table-column>
        <el-table-column prop="modifyTime" label="修改时间" width="100">
          <template slot-scope="scope">
            {{scope.row.modifyTime|timeForm}}
          </template>
        </el-table-column>
        <el-table-column prop="send" label="发送报文">
          <template slot-scope="scope">
            <el-button type="text" @click="sendSpecify(scope.row)">查看详情</el-button>
          </template>
        </el-table-column>
        <el-table-column prop="receive" label="接受报文">
          <template slot-scope="scope">
            <el-button type="text" @click="receiveSpecify(scope.row)">查看详情</el-button>
          </template>
        </el-table-column>

        <el-table-column label="操作" width="200">
          <template slot-scope="scope">
            <el-button size="mini" type="primary" icon="el-icon-edit" @click="bank_mapping_edit(scope.row)"></el-button>
            <el-button size="mini" type="danger" icon="el-icon-delete" @click="bank_mapping_del(scope.$index,scope.row)"></el-button>
          </template>
        </el-table-column>

      </el-table>
    </div>

    <!--
              作者：isst
              时间：2018-08-21
              描述：分页效果
          -->
    <div class="block">
      <span class="demonstration">完整功能</span>
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNo"
        :page-sizes="[4, 8, 12, 16]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="sumCount">
      </el-pagination>
    </div>


    <!--
            作者：isst
            时间：2018-08-23
            描述：弹出报文详情
        -->
    <el-dialog title="" :visible.sync="dialogSendVisible" :data="sendDetails">
      <form class="add-input">
        <h1>发送报文
        </h1><span>当前交易码：{{pyCode}}</span>

            <div class="div-tab">
              <el-table :data="sendDetailss" style="width: 100%" header-row-class-name="center"
                        :default-sort="{prop: 'createDate', order: 'descending'}" >
                <el-table-column prop="[0]" label="属性名" width="100" style="border:0px;background:rgba(0, 0, 0, 0); "   fixed>
                  <template slot-scope="scope">
                    <input v-model="scope.row[0]" v-if="el_icon_edit_details_index!=scope.$index"  disabled="true" class="details-input-off"></input>
                    <input v-model="scope.row[0]" v-else class="details-input-off"></input>
                    <div  v-if="el_icon_edit_details_index!=scope.$index" class="line-off"></div>
                    <div  v-else  class="line-on"></div>
                  </template>
                </el-table-column>
                <el-table-column prop="[1]" label="注释" width="150" fixed>
                  <template slot-scope="scope">
                    <input v-model="scope.row[1]" v-if="el_icon_edit_details_index!=scope.$index"  disabled="true" class="details-input-off"></input>
                    <input v-model="scope.row[1]" v-else class="details-input-off"></input>
                    <div  v-if="el_icon_edit_details_index!=scope.$index" class="line-off"></div>
                    <div  v-else  class="line-on"></div>
                  </template>
                </el-table-column>
                <el-table-column prop="[2]" label="对应译会bean属性" width="150" fixed>
                  <template slot-scope="scope">
                    <input v-model="scope.row[2]" v-if="el_icon_edit_details_index!=scope.$index"  disabled="true" class="details-input-off"></input>
                    <input v-model="scope.row[2]" v-else class="details-input-off"></input>
                    <div  v-if="el_icon_edit_details_index!=scope.$index" class="line-off"></div>
                    <div  v-else  class="line-on"></div>
                  </template>
                </el-table-column>
                <el-table-column prop="[3]" label="默认值" width="150" fixed>
                  <template slot-scope="scope">
                    <input v-model="scope.row[3]" v-if="el_icon_edit_details_index!=scope.$index"  disabled="true" class="details-input-off"></input>
                    <input v-model="scope.row[3]" v-else class="details-input-off"></input>
                    <div  v-if="el_icon_edit_details_index!=scope.$index" class="line-off"></div>
                    <div  v-else  class="line-on"></div>
                  </template>
                </el-table-column>
                <el-table-column prop="[4]" label="标识" width="50" fixed>
                  <template slot-scope="scope">
                    <input v-model="scope.row[4]" v-if="el_icon_edit_details_index!=scope.$index"  disabled="true" class="details-input-off"></input>

                    <input v-model="scope.row[4]" v-else class="details-input-off" @keyup="bank_mapping_check_flag_type(scope.row,scope.column,scope.$index,sendDetailss,'sendDetailss')"></input>
                    <div  v-if="el_icon_edit_details_index!=scope.$index" class="line-off"></div>
                    <div  v-else  class="line-on"></div>
                  </template>
                </el-table-column>

                <el-table-column label="操作" width="230">
                  <template slot-scope="scope">
                    <el-button size="mini" type="primary"  v-if="el_icon_edit_details_index!=scope.$index" icon="el-icon-edit"
                               @click="bank_mapping_edit_details(scope.row,scope.column,scope.$index)"></el-button>
                    <el-button  size="mini" type="primary"  v-else icon="el-icon-check"
                                @click="bank_mapping_edit_details_save(scope.row,scope.column,scope.$index,sendDetailss,'sendDetailss')"></el-button>


                    <el-button  size="mini" type="primary"   icon="el-icon-plus"
                                @click="bank_mapping_details_identical_plus(scope.row,scope.column,scope.$index,sendDetailss,'sendDetailss')">
                    </el-button>

                    <el-button size="mini" type="danger" icon="el-icon-delete" v-if="el_icon_delete_index!=scope.$index"
                               @click="bank_mapping_del_details(scope.$index,sendDetailss)"></el-button>
                    <el-button size="mini" type="danger" v-else
                               @click="bank_mapping_del_details_execute(scope.row,scope.column,scope.$index,sendDetailss,'sendDetailss')">
                      确定
                    </el-button>


                  </template>
                </el-table-column>

              </el-table>
            </div>
      </form>
    </el-dialog>
    <el-dialog title="" :visible.sync="dialogReceiveVisible" :data="receiveDetails">
      <form class="add-input">
        <h1>接受报文
        </h1><span>当前交易码：{{pyCode}}</span>
        <div class="div-tab">
          <el-table :data="receiveDetailss" style="width: 100%" header-row-class-name="center"
                    :default-sort="{prop: 'createDate', order: 'descending'}" >
            <el-table-column prop="[0]" label="属性名" width="100" style="border:0px;background:rgba(0, 0, 0, 0); "   fixed>
              <template slot-scope="scope">
                <input v-model="scope.row[0]" v-if="el_icon_edit_details_index!=scope.$index"  disabled="true" class="details-input-off"></input>
                <input v-model="scope.row[0]" v-else class="details-input-off"></input>
                <div  v-if="el_icon_edit_details_index!=scope.$index" class="line-off"></div>
                <div  v-else  class="line-on"></div>
              </template>
            </el-table-column>
            <el-table-column prop="[1]" label="注释" width="150" fixed>
              <template slot-scope="scope">
                <input v-model="scope.row[1]" v-if="el_icon_edit_details_index!=scope.$index"  disabled="true" class="details-input-off"></input>
                <input v-model="scope.row[1]" v-else class="details-input-off"></input>
                <div  v-if="el_icon_edit_details_index!=scope.$index" class="line-off"></div>
                <div  v-else  class="line-on"></div>
              </template>
            </el-table-column>
            <el-table-column prop="[2]" label="对应译会bean属性" width="150" fixed>
              <template slot-scope="scope">
                <input v-model="scope.row[2]" v-if="el_icon_edit_details_index!=scope.$index"  disabled="true" class="details-input-off"></input>
                <input v-model="scope.row[2]" v-else class="details-input-off"></input>
                <div  v-if="el_icon_edit_details_index!=scope.$index" class="line-off"></div>
                <div  v-else  class="line-on"></div>
              </template>
            </el-table-column>
            <el-table-column prop="[3]" label="默认值" width="150" fixed>
              <template slot-scope="scope">
                <input v-model="scope.row[3]" v-if="el_icon_edit_details_index!=scope.$index"  disabled="true" class="details-input-off"></input>
                <input v-model="scope.row[3]" v-else class="details-input-off"></input>
                <div  v-if="el_icon_edit_details_index!=scope.$index" class="line-off"></div>
                <div  v-else  class="line-on"></div>
              </template>
            </el-table-column>
            <el-table-column prop="[4]" label="标识" width="50" fixed>
              <template slot-scope="scope">
                <input v-model="scope.row[4]" v-if="el_icon_edit_details_index!=scope.$index"  disabled="true" class="details-input-off"></input>

                <input v-model="scope.row[4]" v-else class="details-input-off" @keyup="bank_mapping_check_flag_type(scope.row,scope.column,scope.$index,receiveDetailss,'receiveDetailss')"></input>
                <div  v-if="el_icon_edit_details_index!=scope.$index" class="line-off"></div>
                <div  v-else  class="line-on"></div>
              </template>
            </el-table-column>

            <el-table-column label="操作" width="230">
              <template slot-scope="scope">
                <el-button size="mini" type="primary"  v-if="el_icon_edit_details_index!=scope.$index" icon="el-icon-edit"
                           @click="bank_mapping_edit_details(scope.row,scope.column,scope.$index)"></el-button>
                <el-button  size="mini" type="primary"  v-else icon="el-icon-check"
                           @click="bank_mapping_edit_details_save(scope.row,scope.column,scope.$index,receiveDetailss,'receiveDetailss')"></el-button>


                <el-button  size="mini" type="primary"   icon="el-icon-plus"
                            @click="bank_mapping_details_identical_plus(scope.row,scope.column,scope.$index,receiveDetailss,'receiveDetailss')">
                </el-button>

                <el-button size="mini" type="danger" icon="el-icon-delete" v-if="el_icon_delete_index!=scope.$index"
                           @click="bank_mapping_del_details(scope.$index,receiveDetailss)"></el-button>
                <el-button size="mini" type="danger" v-else
                           @click="bank_mapping_del_details_execute(scope.row,scope.column,scope.$index,receiveDetailss,'receiveDetailss')">
                      确定
                </el-button>
              </template>
            </el-table-column>

          </el-table>
        </div>
      </form>
    </el-dialog>


    <!--
            作者：BelleChou
            时间：2018-07-26
            描述：弹出编辑框
        -->
    <el-dialog :visible.sync="dialogFormVisible" :data="showRow">
      <!--<table class="edit_table" >
                <h1>详情信息</h1>
                <span></span>
                <tr>
                    <td>id</td><td>{{showRow.id}}</td><td><el-input :disabled="true" placeholder="不可更改"></el-input> </td>
                </tr>
                <tr>
                    <td>交易号</td><td>{{showRow.interfaceNo}}</td><td><el-input ></el-input> </td>
                </tr>
                <tr>
                    <td>交易号名称</td><td>{{showRow.remarks}}</td><td><el-input></el-input> </td>
                </tr>
                <tr>
                    <td>创建日期</td><td>{{showRow.createDate|timeForm}}</td><td><el-input :disabled="true" placeholder="不可更改"></el-input></td>
                </tr>
                <tr>
                    <td>修改日期</td><td>{{showRow.modifyTime|timeForm}}</td><td><el-input :disabled="true" placeholder="不可更改"></el-input></td>
                </tr>
                <tr>
                    <td>发送报文</td><td>{{showRow.send}}</td><td><textarea type="text"


                  placeholder="请输入接受报文"
                  rows=10
                ></textarea> </td>
                </tr>
                <tr>
                    <td>接受报文</td><td>{{showRow.receive}}</td><td><textarea type="textarea"


                  placeholder="请输入发送报文"
                 rows=10
                ></textarea></td>
                </tr>
            </table>-->
      <form class="add-input">

        <h1>编辑记录
        </h1>
        <el-row :gutter="20">

          <el-col :span="12">
            <div>
              <span>交易号 </span>
              <input v-model="editInterfaceNo" type="text" name="name"/>
            </div>
          </el-col>

          <el-col :span="12">
            <div>
              <span>交易号名称 </span>
              <input v-model="editRemarks" type="text" name="email"/>
            </div>
          </el-col>

        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <div><span>输入报文 </span>
              <textarea v-model="editSend" style="height: 500px;"></textarea></div>
          </el-col>
          <el-col :span="12">
            <div><span>输出报文 </span>
              <textarea v-model="editReceive" style="height: 500px;"></textarea></div>
          </el-col>
        </el-row>
      </form>
      <button @click="bank_mapping_update">确定</button>
    </el-dialog>

    <footer-nav v-bind:class="{'isManage':isNowPage}"></footer-nav>

  </div>
</template>

<script>
  import FooterNav from '@/components/footer.vue'
  import {BankData} from '@/assets/js/BankData'
  var cells="";  //js原生全局
  export default {
    components: {
      FooterNav
    },
    data() {
      return {
        isNowPage: true,
        showAdd: false,
        bankLists: [],
        showEdit: false,
        pageNo: 1,
        pageSize: 4,
        sumCount: 1,
        interfaceNo: '',
        remarks: '',
        writer: '',
        send: '',
        receive: '',
        pages: 1,
        dialogFormVisible: false,
        editRow: '',
        showRow: '',
        showQuery: false,
        inputInterfaceNoQuery: '',
        sendDetails: "",
        sendDetailss: [],
        dialogSendVisible: false,
        receiveDetails: "",
        receiveDetailss: [],
        dialogReceiveVisible: false,
        editInterfaceNo: '',
        editSend: '',
        editReceive: '',
        editWriter: '',
        editRemarks: '',
        pyCode: '',
        el_icon_edit_details_index:-1,    //编辑确认标识
        currNewRow:'',
        snewRow:'',
        el_icon_delete_index:-1      //删除确认标识

      }
    },
    created() {
      //查询列表方法
      this.bank_mapping_query();
    },
    methods: {
      sendSpecify(sendDetails) {
        this.showRow = sendDetails;
        this.pyCode = sendDetails.id;
        sendDetails = sendDetails.send;
        var one = sendDetails.split("\n");
        var lenths = 0;
        if (one[one.length - 1].split(",").length > 2) {
          lenths = one.length;
        } else {
          lenths = one.length - 1;
        }
        sendDetails = new Array(lenths);
        for (var i = 0; i < lenths; i++) {
          sendDetails[i] = one[i].split(",");
        }
        this.sendDetailss = sendDetails;
        this.dialogSendVisible = true;
      },
      receiveSpecify(receiveDetails) {
        this.showRow = receiveDetails;
        this.pyCode = receiveDetails.id;
        receiveDetails = receiveDetails.receive;
        var one = receiveDetails.split("\n");
        var lenths = 0;
        if (one[one.length - 1].split(",").length > 2) {
          lenths = one.length;
        } else {
          lenths = one.length - 1;
        }
        receiveDetails = new Array(lenths);
        for (var i = 0; i < lenths; i++) {
          receiveDetails[i] = one[i].split(",");
        }
       this.receiveDetailss = receiveDetails;
        this.dialogReceiveVisible = true;
      },
      queryOneItem() {
        this.dialogFormVisible = true;

        let parames = {
          "functionName": "bankinterfacebiz.service.BankMappingService",
          "methodName": "queryBankMapping",
          "data": {
            "interfaceNo": this.inputInterfaceNoQuery
          }
        };
        this.$axios({
          method: 'post',
          url: '/CallMethod',
          data: JSON.stringify(parames),
          headers: {
            'accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
          }
        }).then(res => {
          if (res.data.errorCode == 'ERRORCODE0000') { //0成功，否则失败
            this.showRow = res.data.data;
          }
        }).catch(error => {

        })
      },
      handleSizeChange(val) {
        this.pageSize = val;
        this.bank_mapping_query();
      },
      handleCurrentChange(val) {
        this.pageNo = val;
        this.bank_mapping_query();
      },
      indexMethod(index) {
        return (this.pageNo - 1) * this.pageSize + 1 + index;
      },
      //列表选择行方法
      handleRowChange(row, event, column) {
        let index = this.bankLists.indexOf(row) + (this.pageNo - 1) * this.pageSize;
        this.tempData = row;
      },
      //分页查询
      bank_mapping_query() {
        let parames = {
          "functionName": "bankinterfacebiz.service.BankMappingService",
          "methodName": "queryPage",
          "pageNo": this.pageNo,
          "pageSize": this.pageSize,
          "data": {
            "interfaceNo": this.interfaceNo,
            "writer": this.writer
          }
        };
        this.$axios({
          method: 'post',
          url: '/CallMethod',
          data: JSON.stringify(parames),
          headers: {
            'accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
          }
        }).then(res => {
          if (res.data.errorCode == 'ERRORCODE0000') { //0成功，否则失败
            if (res.data.data.list.length > 0) {
              this.sumCount = res.data.sumCount;
              this.bankLists = res.data.data.list;
              //console.log(this.bankLists);

            } else {
              this.bankLists = [];

            }
          } else {
            this.bankLists = [];
          }
        }).catch(error => {
        })
      },
      bank_mapping_query1() {
        let parames = {
          "functionName": "bankinterfacebiz.service.BankMappingService",
          "methodName": "queryBankMapping",
          "data": {
            "interfaceNo": this.inputInterfaceNoQuery
          }
        };
        this.$axios({
          method: 'post',
          url: '/CallMethod',
          data: JSON.stringify(parames),
          headers: {
            'accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
          }
        }).then(res => {
          if (res.data.errorCode == 'ERRORCODE0000') { //0成功，否则失败

            if (res.data.data != undefined) {
              this.sumCount = res.data.sumCount;
              this.bankLists = [{
                id: res.data.data.id,
                interfaceNo: res.data.data.interfaceNo,
                remarks: res.data.data.remarks,
                writer: res.data.data.writer,
                createDate: res.data.data.createDate,
                modifyTime: res.data.data.modifyTime,
                send: res.data.data.send,
                receive: res.data.data.receive
              }];

              console.log(bankLists)


            } else {
              this.bankLists = [];

            }
          } else {
            this.bankLists = [];

          }
        }).catch(error => {

        })

      },
      add() {
        this.showAdd = true
      },
      formatterOS(row, value, column) {
        return column;
      }
      ,
      bank_mapping_add() {
        let parames = {
          "functionName": "bankinterfacebiz.service.BankMappingService",
          "methodName": "addBankMappingString",
          "data": {
            "interfaceNo": this.interfaceNo,
            "writer": "测试人员",
            "remarks": this.remarks,
            "send": this.send,
            "receive": this.receive
          }
        };
        this.$axios({
          method: 'post',
          url: '/CallMethod',
          data: JSON.stringify(parames),
          headers: {
            'accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
          }
        }).then(res => {
          if (this.interfaceNo.trim() == "") {
            alert("交易码不能为空！");
          }
//					else if(this.writer.trim()==""){
//						alert("作者不能为空！");
//					}
          else if (this.remarks.trim() == '') {
            alert("备注不能为空！")
          }
          else if (this.send.trim() == '') {
            alert("发送报文不能为空！")
          }
          else if (this.send.trim() == '') {
            alert("接受报文不能为空！")
          }

          if (res.data.errorCode == 'ERRORCODE0000') {
//						alert("新增成功")
            this.$message({
              type: 'success',
              message: '新增成功!'
            });
            this.interfaceNo ="",
               this.writer="";
            this.bank_mapping_query();
          }
          else {
            this.$message({
              type: 'error',
              message: '新增失败!'
            });
          }
          this.showAdd = false;
        }).catch(error => {

          console.log(error);
        })

      },
      //删除
      bank_mapping_del(index,delRow) {
        var id = delRow.id;
        let parames = {
          "functionName": "bankinterfacebiz.service.BankMappingService",
          "methodName": "delOneBankMapping",
          "data": {
            "id": delRow.id.toString()  //id是Number  要转换成String
          }
        };

        this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$axios({
            method: 'post',
            url: '/CallMethod',
            data: JSON.stringify(parames),
            headers: {
              'accept': 'application/json',
              'Content-Type': 'application/json;charset=UTF-8'
            }
          }).then(res => {
            if (res.data.errorCode == 'ERRORCODE0000') { //0成功，否则失败
              this.$message({
                type: 'success',
                message: '删除成功!'
              });
              delRow.splice(index,1); //移除掉传入的哪一行
            }
            else {
              console.log("fail")
            }
          }).catch(error => {

          })
          this.bank_mapping_query();
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });

      },
      //edit
      bank_mapping_edit(editRow) {
        this.dialogFormVisible = true;
        this.showRow = editRow;  //展示
        this.editInterfaceNo = this.showRow.interfaceNo;
        this.editRemarks = this.showRow.remarks;
        this.editSend = this.showRow.send;
        this.editReceive = this.showRow.receive;
      },
      //edit 查看详情
      bank_mapping_edit_details(editRow,column,index) {
        this.el_icon_delete_index=-1;
        this.el_icon_edit_details_index=index;
      },
      //详情中的保存
      bank_mapping_edit_details_save(editRow,column,index,thisObje,saveType) {
        this.el_icon_delete_index =-1;
        var allArr = "";
        for (var i=0;i< thisObje.length;i++ ){

          for (var j=0;j<thisObje[i].length;j++ ){
            if(j<thisObje[i].length-1){
              allArr += thisObje[i][j]+",";
            }else{
              allArr += thisObje[i][j];
            }

          }
          allArr+="\n";
        }
        let parames ="";
      if(saveType=="receiveDetailss"){
          this.showRow.receive = allArr;
          parames = {
            "functionName": "bankinterfacebiz.service.BankMappingService",
            "methodName": "updateBankMappingString",
            "data": {
              "id":this.pyCode,
              "receive": allArr
            }
          };
      }else{
        this.showRow.send = allArr;
        parames = {
          "functionName": "bankinterfacebiz.service.BankMappingService",
          "methodName": "updateBankMappingString",
          "data": {
            "id":this.pyCode,
            "send": allArr
          }
        };
      }
      this.el_icon_edit_details_index=-1;
        this.$axios({
          method: 'post',
          url: '/CallMethod',
          data: JSON.stringify(parames),
          headers: {
            'accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
          }
        }).then(res => {
          if (res.data.errorCode == 'ERRORCODE0000') { //0成功，否则失败
            this.$message({
              type: 'success',
              message: '修改成功!'
            });
          }
          else {
            this.$message({
              type: 'error',
              message: '修改失败!'
            });
          }
        }).catch(error => {
        })
    },
      //增加一行
      bank_mapping_details_identical_plus(editRow,column,index,thisObje,saveType) {
        this.currNewRow =  new Array(5);
        thisObje.splice(index+1,0,this.currNewRow);
        this.el_icon_edit_details_index=index+1;
      },
      //remove
      bank_mapping_del_details(index, delRow) {
        this.el_icon_delete_index = index;

      },
      bank_mapping_del_details_execute(editRow,column,index,thisObje,saveType){
        thisObje.splice(index,1);
        this.bank_mapping_edit_details_save(editRow,column,index,thisObje,saveType);
        this.el_icon_delete_index =-1;
      },

      //检查用户输入的时候类型的 标识
      bank_mapping_check_flag_type(editRow,column,index,thisObje,saveType){
        if(editRow[4]=='1' ){
          this.snewRow = new Array(this.currNewRow[0],this.currNewRow[1],this.currNewRow[2],this.currNewRow[3],this.currNewRow[4]);
          this.snewRow[4] = -1;
          thisObje.splice(index+1,0, this.snewRow);
          thisObje.splice(index+1,0, new Array(5));
          this.el_icon_edit_details_index=index+1;
        }else if(editRow[4]=='0'){

        }else if(editRow[4]=='-1'){

        }else{
          this.$message({
            type: 'warning',
            message: '输入有误！！！请检查：0 单个元素 1 对象元素'
          });
        }
      },
      //update
      bank_mapping_update() {
        this.dialogFormVisible = true;
        let parames = {
          "functionName": "bankinterfacebiz.service.BankMappingService",
          "methodName": "updateBankMappingString",
          "data": {
            "id": this.showRow.id.toString(),
            "interfaceNo": this.editInterfaceNo ? this.editInterfaceNo : this.showRow.interfaceNo,
            "writer": this.editWriter ? this.editWriter : this.showRow.interfaceNo,
            "send": this.editSend,
            "receive": this.editReceive,
            "remarks": this.editRemarks ? this.editRemarks : this.showRow.editRemarks
          }
        };
        this.$axios({
          method: 'post',
          url: '/CallMethod',
          data: JSON.stringify(parames),
          headers: {
            'accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
          }
        }).then(res => {
          if (res.data.errorCode == 'ERRORCODE0000') { //0成功，否则失败
            this.$message({
              type: 'success',
              message: '修改成功!'
            });
            //这里 不明白
            this.showRow.interfaceNo = this.editInterfaceNo;
            this.showRow.remarks = this.editRemarks;
            this.showRow.send = this.editSend;
            this.showRow.receive = this.editReceive;

            this.dialogFormVisible = false;
            console.log(res.data.errorCode)
          }
          else {
            this.$message({
              type: 'error',
              message: '修改失败!'
            });
            console.log(res.data.errorCode)
          }

        }).catch(error => {


        })

      }


    }

  }
</script>

<style>



  .line-on{
    color: #555;
    line-height: 10px;
    margin-top: 0px;
    margin-left: 7px;
    padding: 0px;
    text-align:center;
    outline: 0;
    height: 2px;
    font-size: 16px;
    border: 0px;
    border-bottom: 1px solid #2cccff;
    background:rgba(0, 0, 0,0);
    animation: ad_width .6s linear forwards;
  }
  @keyframes ad_width {
    from {
      width: 0%
    }

    to {
      width: 100%
    }
  }
  .line-off{
    color: #555;
    line-height: 10px;
    width: 100%;
    margin-top: 0px;
    text-align:center;
    outline: 0;
    height: 2px;
    font-size: 16px;
    border: 0px solid #E5E5E5;
    background:rgba(0, 0, 0, 0);
  }
  .details-input-off{
    color: #555;
    line-height: 10px;
    width: 100%;
    margin-top: 0px;
    text-align:center;
    outline: 0;
    height: 22px;
    font-size: 16px;
    border: 0px solid #E5E5E5;
    background:rgba(0, 0, 0, 0);
  }



</style>
























<style>
  .manage {
    padding-bottom: 50px;
  }

  .manage > button {
    width: 200px;
    height: 40px;
    line-height: 50%;
    background-color: #41b883;
    border: none;
    border-radius: 5px;
    font-size: 16px;
    color: #fff;
  }

  table {
    width: 100%;
    max-width: 500px;
    margin: 0 auto;
    margin-top: 20px;
  }

  .input-area input {
    width: 200px;
    height: 50px;
    line-height: 40px;
    margin: 20px 0;
    outline: none;
    border: 1px solid #333;
  }

  button {
    width: 60px;
    height: 40px;
    line-height: 40px;
    background-color: #41b883;
    border: none;
    border-radius: 5px;
    font-size: 16px;
    color: #fff;
  }

  th,
  td {
    width: 100px;
  }

  tr input {
    width: 100px;
    height: 10px;
    padding-left: 10px;
    outline: none;
    border: 1px solid #333;
  }

  .wrap {
    display: table;
    position: fixed;
    top: 0;
    left: 0;
    height: 100%;
    width: 100%;
    background: rgba(0, 0, 0, .8);
    z-index: 10;
  }

  .wrap .content {
    display: table-cell;
    vertical-align: middle;
  }

  .wrap .content input {
    height: 10px;
    line-height: 40px;
    display: block;
    margin: 0 auto;
    margin-bottom: 10px;
    font-size: 16px;
  }

  .wrap .content button {
    width: 100px;
    height: 30px;
    line-height: 30px;
    background-color: #41b883;
    border: none;
    border-radius: 5px;
    font-size: 16px;
    color: #fff;
  }

  .el-row {
    margin-bottom: 20px;

  &
  :last-child {
    margin-bottom: 0;
  }

  }
  .el-col {
    border-radius: 4px;
  }

  .bg-purple-dark {
    background: #99a9bf;
  }

  .bg-purple {
    background: #d3dce6;
  }

  .bg-purple-light {
    background: #e5e9f2;
  }

  .grid-content {
    border-radius: 4px;
    min-height: 36px;
  }

  .row-bg {
    padding: 10px 0;
    background-color: #f9fafc;
  }

  .edit_table {
    width: 100%;

  }

  .edit_table, .edit_table tr {
    border-top-width: 1px;
    border-top-style: solid;
    border-top-color: rgb(235, 242, 224);
  }

  .edit_table {
    border-bottom-width: 1px;
    border-bottom-style: solid;
    border-bottom-color: rgb(235, 242, 224);
  }

  /* Padding and font style */
  .edit_table td {
    padding: 5px 10px;
    font-size: 15px;
    font-family: Verdana;

  }

  .edit_table th {
    font-size: 20px;
  }

  /* Alternating background colors */
  .edit_table tr:nth-child(even) {
    background: rgb(230, 238, 214)
  }

  .edit_table tr:nth-child(odd) {
    background: #FFF
  }

  .el-dialog__headerbtn .el-dialog__close {
    color: #00ff86;
  }

  .el-dialog__headerbtn {
    position: absolute;
    top: -10px;
    right: -10px;
    padding: 0;
    background: 0 0;
    border: none;
    outline: 0;
    cursor: pointer;
    font-size: 16px;
  }

  .element.style {
  }

  .el-dialog__body {
    padding: 0px 0px;
    color: #606266;
    font-size: 14px;
  }

  .el-dialog__header {
    padding: 0;
  }

  textarea {
    resize: none;
  }

  .add-input {
    margin-left: auto;
    margin-right: auto;
    max-width: 100%;
    background: #F8F8F8;
    padding: 30px 30px 20px 30px;
    /*font: 12px Arial, Helvetica, sans-serif;*/
    color: #666;
    border-radius: 5px;
    -webkit-border-radius: 5px;
    -moz-border-radius: 5px;
  }

  .add-input h1, .edit_table h1 {
    padding: 20px 0px 20px 40px;
    display: block;
    margin: -40px -30px 10px -30px;
    color: #FFF;
    background: #41b883;
    text-shadow: 1px 1px 1px #949494;
    border-radius: 5px 5px 0px 0px;
    -webkit-border-radius: 5px 5px 0px 0px;
    -moz-border-radius: 5px 5px 0px 0px;
    border-bottom: 1px solid #89AF4C;

  }

  .add-input h1 > span {
    display: block;
    font-size: 11px;
    color: #FFF;
  }

  .edit_table h1 > span {
    display: block;
    font-size: 11px;
    color: #FFF;
  }

  .add-input label {
    display: block;
    margin: 0px 0px 5px;
  }

  .add-input label > span {
    float: left;
    margin-top: 10px;
    color: #5E5E5E;
  }

  .add-input input[type="text"], .add-input input[type="email"], .add-input textarea, .add-input select {
    color: #555;
    height: 30px;
    line-height: 15px;
    width: 100%;
    padding: 0px 0px 0px 10px;
    margin-top: 2px;
    border: 1px solid #E5E5E5;
    background: #FBFBFB;
    outline: 0;
    -webkit-box-shadow: inset 1px 1px 2px rgba(238, 238, 238, 0.2);
    box-shadow: inset 1px 1px 2px rgba(238, 238, 238, 0.2);
    /*font: normal 14px/14px Arial, Helvetica, sans-serif;*/
  }

  .add-input textarea {
    height: 100px;
    padding-top: 10px;
  }

  .add-input select {
    /*background: url('down-arrow.png') no-repeat right, -moz-linear-gradient(top, #FBFBFB 0%, #E9E9E9 100%);
      background: url('down-arrow.png') no-repeat right, -webkit-gradient(linear, left top, left bottom, color-stop(0%,#FBFBFB), color-stop(100%,#E9E9E9));*/
    appearance: none;
    -webkit-appearance: none;
    -moz-appearance: none;
    text-indent: 0.01px;
    text-overflow: '';
    width: 100%;
    height: 30px;
  }

  .center th .cell {
    text-align: center !important;
    font-size: 15px;
    margin-top: 30px;
  }
</style>
