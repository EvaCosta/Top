//onload simplificada jquery
$(function(){
  
  initialize();

});

function intervalRefresh(){
  var seconds = $("#refresh").val() * 1000;
  setInterval(initialize, seconds);
}

//Popula todos os componentes da dashboard com valores recuperados da API.
function initialize(){

  const urlAPI = "https://api-java-top.herokuapp.com/top";

  $.ajax({
    async: true,
    url: urlAPI,
    method: "GET",

    success:function(response){

      console.log(response);

      runtime(response.time, response.numberOfUsers, response.numberOfTasks, response.runningTasks)

      for (let index = 0; index < response.processes.length; index++) {
      
        appendLine(response.processes[index].id,response.processes[index].user,response.processes[index].command);
      
      } 
      
      addSystemMemory(response.systemMemory.total,
                             response.systemMemory.free,
                             response.systemMemory.used,
                             response.systemMemory.bufferCache);
      


                             
      // ------- Adicionar as funções de preenchimento dos cards aqui -------

      addLoadAverage(response.loadAverage.oneMinute, response.loadAverage.fiveMinutes, response.loadAverage.fifteenMinutes);

      addInfoCPU(response.cpuUsage.user,response.cpuUsage.system, response.cpuUsage.idleProcess, response.cpuUsage.ioWait);

      addInfoSwapMemory(response.swapMemory.total, response.swapMemory.used, response.swapMemory.free, response.swapMemory.available);	


      /*Define clique dos botes da tabela e chama função
      de completar as informações do modal.*/
      var clicado = null, nome = null;
      $('.clicado').click(function () {
          pid = $(this).parents('tr').find('th').eq(0).text();
          
          searchForProcess(pid);

      });

    },
    error: function(erro){
        alert("Erro ao acessar a API");
    },

  });

}

/*
  Pesquisa pelo pid do processo fazendo uma requisição ajax na api. 
  Após recuperar os dados as tabelas preenchidas.
*/
function searchForProcess(pid){
  const urlAPIProcess = "https://api-java-top.herokuapp.com/top/process/";

  $.ajax({
      
    async: true,
    url: urlAPIProcess+pid,
    method: "GET",

    success:function(response){

      addCompleteModal(pid,response.user,response.priority,response.niceLevel,response.virtualMemoryUsed,response.residentMemoryUsed,
        response.percentageOfCpuUsed,response.percentageOfMemoryUsed,response.shareableMemory,response.command,response.upTime,response.state);
    
    },

    error: function(erro){
      alert("Erro ao acessar a API");
    },

  });
}

/*insere componentes de exemplo na tabela
  de processos do dashboard*/
  
/*Insere dados na modal do processo completo.
  
  A esqueda parametros na ordem de inserção
  a direita chave do processo no json.
    pid->pid
    user->user
    priority->priority
    nicelevel->niceLevel
    virtmemused->virtualMemoryUsed
    resmemused->residentMemoryUsed
    cpuused->percentageOfCpuUsed
    menused->percentageOfMemoryUsed
    sharmen->shareableMemory
    command->command
    uptime->upTime
    state->state
  */
function addCompleteModal(pid,user,priority,nicelevel,virtmemused,resmemused,cpuused,menused,sharmen,command,uptime,state){
  $('#pidmodal').text(pid);
  $('#usermodal').text(user);
  $('#priomodal').text(priority);
  $('#nilvlmodal').text(nicelevel);
  $('#vmumodal').text(virtmemused);
  $('#rmumodal').text(resmemused);
  $('#pcumodal').text(cpuused);
  $('#pmumodal').text(menused);
  $('#shmmodal').text(sharmen);
  $('#cmdmodal').text(command);
  $('#tmmodal').text(uptime);
  $('#stsmodal').text(state);
}


/*Adiciona linha na tabela de processos

  A esqueda parametros na ordem de inserção
  a direita chave do processo no json.

    pid->pid
    user->user
    command->command
*/   
function appendLine(pid,user,command) {
  $('#process').append('<tr>'+
      '<th scope="row">'+pid+'</th>'+
      '<td>'+user+'</td>'+
      '<td>'+command+'</td>'+
    '<td>'+
          '<button type="button" class="btn btn-outline-info clicado" data-bs-toggle="modal" data-bs-target="#modal">'+
              'Complet Data'+
          '</button>'+
      '</td>'+
  '</tr>');
}

/*Adiciona os dados da memória do sistema no card correspondente.
  A esqueda parametros na ordem de inserção
  a direita chave do processo no json.
  
    total->total
    free->free
    used->used
    buffer->buffer
*/
function addSystemMemory(total, free, used, buffer){

  $("#memoriaTotal").html(total);
  $("#memoriaLivre").html(free);
  $("#memoriaEmUso").html(used);
  $("#buffer").html(buffer);

}

function runtime(time, users, tasks, exec){
  $("#tExecucao").html(time);
  $("#nUsuarios").html(users);
  $("#nTarefas").html(tasks);
  $("#pExecucao").html(exec);
  
}


function addLoadAverage(oneMinute, fiveMinutes, fifteenMinutes){
  $("#bCarga1").html(oneMinute.toPrecision(4));
  $("#bCarga5").html(fiveMinutes.toPrecision(4));
  $("#bCarga15").html(fifteenMinutes.toPrecision(4));
}

function addInfoCPU(usuario, sistema, idleProcess, ioWait){
  $("#cpuUsuario").html(usuario);
  $("#cpuSistema").html(sistema);
  $("#cpuOcioso").html(idleProcess);
  $("#cpuMenPrioridade").html(ioWait);
}

function addInfoSwapMemory(total, livre, uso, disponivel){
  $("#swapTotal").html(total);
  $("#swapLivre").html(livre);
  $("#swapEmUso").html(uso);
  $("#swapDisponivel").html(disponivel);
}
