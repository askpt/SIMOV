namespace SIMOV_WS.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class UpdateNullableDateTime : DbMigration
    {
        public override void Up()
        {
            AlterColumn("dbo.Pacientes", "Data", c => c.DateTime());
        }
        
        public override void Down()
        {
            AlterColumn("dbo.Pacientes", "Data", c => c.DateTime(nullable: false));
        }
    }
}
