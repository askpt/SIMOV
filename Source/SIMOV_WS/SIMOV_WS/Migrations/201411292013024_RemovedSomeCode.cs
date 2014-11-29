namespace SIMOV_WS.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class RemovedSomeCode : DbMigration
    {
        public override void Up()
        {
            RenameColumn(table: "dbo.HistoricoAlertas", name: "PacientesID", newName: "PacienteID");
            RenameColumn(table: "dbo.Marcacaos", name: "PacientesID", newName: "PacienteID");
            RenameColumn(table: "dbo.Pacientes", name: "Responsavels_ID", newName: "Responsavel_ID1");
            RenameIndex(table: "dbo.HistoricoAlertas", name: "IX_PacientesID", newName: "IX_PacienteID");
            RenameIndex(table: "dbo.Marcacaos", name: "IX_PacientesID", newName: "IX_PacienteID");
            RenameIndex(table: "dbo.Pacientes", name: "IX_Responsavels_ID", newName: "IX_Responsavel_ID1");
        }
        
        public override void Down()
        {
            RenameIndex(table: "dbo.Pacientes", name: "IX_Responsavel_ID1", newName: "IX_Responsavels_ID");
            RenameIndex(table: "dbo.Marcacaos", name: "IX_PacienteID", newName: "IX_PacientesID");
            RenameIndex(table: "dbo.HistoricoAlertas", name: "IX_PacienteID", newName: "IX_PacientesID");
            RenameColumn(table: "dbo.Pacientes", name: "Responsavel_ID1", newName: "Responsavels_ID");
            RenameColumn(table: "dbo.Marcacaos", name: "PacienteID", newName: "PacientesID");
            RenameColumn(table: "dbo.HistoricoAlertas", name: "PacienteID", newName: "PacientesID");
        }
    }
}
