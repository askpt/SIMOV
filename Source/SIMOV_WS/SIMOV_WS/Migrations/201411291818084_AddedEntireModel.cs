namespace SIMOV_WS.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class AddedEntireModel : DbMigration
    {
        public override void Up()
        {
            DropForeignKey("dbo.Pacientes", "Responsavel_ID", "dbo.Responsavels");
            DropIndex("dbo.Pacientes", new[] { "Responsavel_ID" });
            CreateTable(
                "dbo.Alertas",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        Designacao = c.String(),
                        HoraSincronizacao = c.String(),
                    })
                .PrimaryKey(t => t.Id);
            
            CreateTable(
                "dbo.HistoricoAlertas",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        Data = c.DateTime(nullable: false),
                        Longitude = c.Double(nullable: false),
                        Latitude = c.Double(nullable: false),
                        Local = c.String(),
                        HoraSincronizacao = c.DateTime(nullable: false),
                        AlertaId = c.Int(),
                        PacientesID = c.Int(),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.Alertas", t => t.AlertaId)
                .ForeignKey("dbo.Pacientes", t => t.PacientesID)
                .Index(t => t.AlertaId)
                .Index(t => t.PacientesID);
            
            CreateTable(
                "dbo.Marcacaos",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        TipoMarcacao = c.String(),
                        Data = c.DateTime(nullable: false),
                        Longitude = c.Double(nullable: false),
                        Latitude = c.Double(nullable: false),
                        Local = c.String(),
                        HoraSincronizacao = c.DateTime(nullable: false),
                        PacientesID = c.Int(),
                        EstadoMarcacaoId = c.Int(nullable: false),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.EstadoMarcacaos", t => t.EstadoMarcacaoId, cascadeDelete: true)
                .ForeignKey("dbo.Pacientes", t => t.PacientesID)
                .Index(t => t.PacientesID)
                .Index(t => t.EstadoMarcacaoId);
            
            CreateTable(
                "dbo.EstadoMarcacaos",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        Designacao = c.String(),
                        HoraSincronizacao = c.DateTime(nullable: false),
                    })
                .PrimaryKey(t => t.Id);
            
            AddColumn("dbo.Pacientes", "Data", c => c.DateTime(nullable: false));
            AddColumn("dbo.Pacientes", "Responsavels_ID", c => c.Int());
            AlterColumn("dbo.Pacientes", "Responsavel_ID", c => c.Int(nullable: false));
            CreateIndex("dbo.Pacientes", "Responsavels_ID");
            AddForeignKey("dbo.Pacientes", "Responsavels_ID", "dbo.Responsavels", "ID");
            DropColumn("dbo.Pacientes", "DataLocal");
        }
        
        public override void Down()
        {
            AddColumn("dbo.Pacientes", "DataLocal", c => c.DateTime(nullable: false));
            DropForeignKey("dbo.Pacientes", "Responsavels_ID", "dbo.Responsavels");
            DropForeignKey("dbo.Marcacaos", "PacientesID", "dbo.Pacientes");
            DropForeignKey("dbo.Marcacaos", "EstadoMarcacaoId", "dbo.EstadoMarcacaos");
            DropForeignKey("dbo.HistoricoAlertas", "PacientesID", "dbo.Pacientes");
            DropForeignKey("dbo.HistoricoAlertas", "AlertaId", "dbo.Alertas");
            DropIndex("dbo.Marcacaos", new[] { "EstadoMarcacaoId" });
            DropIndex("dbo.Marcacaos", new[] { "PacientesID" });
            DropIndex("dbo.Pacientes", new[] { "Responsavels_ID" });
            DropIndex("dbo.HistoricoAlertas", new[] { "PacientesID" });
            DropIndex("dbo.HistoricoAlertas", new[] { "AlertaId" });
            AlterColumn("dbo.Pacientes", "Responsavel_ID", c => c.Int());
            DropColumn("dbo.Pacientes", "Responsavels_ID");
            DropColumn("dbo.Pacientes", "Data");
            DropTable("dbo.EstadoMarcacaos");
            DropTable("dbo.Marcacaos");
            DropTable("dbo.HistoricoAlertas");
            DropTable("dbo.Alertas");
            CreateIndex("dbo.Pacientes", "Responsavel_ID");
            AddForeignKey("dbo.Pacientes", "Responsavel_ID", "dbo.Responsavels", "ID");
        }
    }
}
