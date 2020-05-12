let addon = {
  preFlight: function (context) {
    console.log('preFlight', context)
  },
  nodes: [],
  pods: [],
  containers: []
}

function openPodMonitor(context){
	console.log('点击调整pms系统', context)
	window.open("www.baidu.com", '_blank')
}

let podMonitor = {
  title: "跳转融媒体管理系统",
  icon: 'el-skip-cm-sites-pms',
  loading: false,
  visible: function (context) {
    // console.log('检查节点监控是否可见 pod', context)
    return true
  },
  openMonitoringPage: openPodMonitor
}

addon.pods.push(podMonitor)

window.EIP_MONITOR_ADDON_TO_ACTIVATE = addon
