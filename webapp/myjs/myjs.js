//onload simplificada jquery
$(function(){
    /*insere componentes de exemplo na tabela
      de processos do dashboard*/
    p='100'
    u='user'
    c='command'
    for (let index = 0; index < 10; index++) {
        appendLine(p+index,u+index,c+index);
    }

    /*Define clique dos botes da tabela e chama função
      de completar as informações do modal.*/
    var clicado = null, nome = null;
    $('.clicado').click(function () {
        pid = $(this).parents('tr').find('th').eq(0).text();
        
        addCompleteModal(pid,'VIN','100','8','3832716','176920','4','2.2','122856','kwin_x11','6:12.35','S');
    });
    
    /*adiciona linha na tabela de processos
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
                '<button type="button" class="btn btn-primary clicado" data-bs-toggle="modal" data-bs-target="#modal">'+
                    'Complet Data'+
                '</button>'+
            '</td>'+
        '</tr>');
    }

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

});




