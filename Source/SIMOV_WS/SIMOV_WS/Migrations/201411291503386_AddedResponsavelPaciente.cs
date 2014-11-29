namespace SIMOV_WS.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class AddedResponsavelPaciente : DbMigration
    {
        public override void Up()
        {
            CreateTable(
                "dbo.Pacientes",
                c => new
                    {
                        ID = c.Int(nullable: false, identity: true),
                        Nome = c.String(),
                        Apelido = c.String(),
                        Email = c.String(),
                        ContactoTlf = c.String(),
                        Longitude = c.Single(nullable: false),
                        Latitude = c.Single(nullable: false),
                        NomeLocal = c.String(),
                        DataLocal = c.DateTime(nullable: false),
                        Ativo = c.Boolean(nullable: false),
                        HoraSincronizacao = c.DateTime(nullable: false),
                        Responsavel_ID = c.Int(),
                    })
                .PrimaryKey(t => t.ID)
                .ForeignKey("dbo.Responsavels", t => t.Responsavel_ID)
                .Index(t => t.Responsavel_ID);
            
            CreateTable(
                "dbo.Responsavels",
                c => new
                    {
                        ID = c.Int(nullable: false, identity: true),
                        Nome = c.String(),
                        Apelido = c.String(),
                        ContactoTlf = c.String(),
                        NotifMail = c.Boolean(nullable: false),
                        NotifSms = c.Boolean(nullable: false),
                        PeriodDiurna = c.Int(nullable: false),
                        PeriodNoturna = c.Int(nullable: false),
                        Email = c.String(),
                        Password = c.String(),
                        HoraSincronizacao = c.DateTime(nullable: false),
                    })
                .PrimaryKey(t => t.ID);
            
            DropTable("dbo.People");
        }
        
        public override void Down()
        {
            CreateTable(
                "dbo.People",
                c => new
                    {
                        ID = c.Int(nullable: false, identity: true),
                        Name = c.String(),
                        Surname = c.String(),
                        Age = c.Int(nullable: false),
                    })
                .PrimaryKey(t => t.ID);
            
            DropForeignKey("dbo.Pacientes", "Responsavel_ID", "dbo.Responsavels");
            DropIndex("dbo.Pacientes", new[] { "Responsavel_ID" });
            DropTable("dbo.Responsavels");
            DropTable("dbo.Pacientes");
        }
    }
}
