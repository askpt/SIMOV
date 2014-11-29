namespace SIMOV_WS.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class RemovedSomeCode1 : DbMigration
    {
        public override void Up()
        {
            AlterColumn("dbo.Alertas", "HoraSincronizacao", c => c.DateTime(nullable: false));
        }
        
        public override void Down()
        {
            AlterColumn("dbo.Alertas", "HoraSincronizacao", c => c.String());
        }
    }
}
